package com.dreamsoftware.nimbustv.framework.epg

import android.util.Log
import com.dreamsoftware.nimbustv.domain.exception.ParseEpgFailedException
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeDataBO
import com.dreamsoftware.nimbustv.domain.service.IEpgParserService
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.IOException
import java.io.InputStream
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.UUID
import java.util.zip.GZIPInputStream

internal class EpgParserServiceImpl(
    private val httpClient: OkHttpClient,
    private val dispatcher: CoroutineDispatcher
) : IEpgParserService {

    private companion object {
        const val TAG = "EPG_PARSER"
        const val PROGRAMME_CHANNEL_ATTR = "channel"
        const val PROGRAMME_START_ATTR = "start"
        const val PROGRAMME_STOP_ATTR = "stop"
        const val CHANNEL_ENTRY_NAME = "channel"
        const val PROGRAMME_ENTRY_NAME = "programme"
    }

    @Throws(ParseEpgFailedException::class)
    override suspend fun parseEpgData(profileId: String, url: String): List<EpgDataBO> =
        withContext(dispatcher) {
            try {
                downloadFile(url)?.let {
                    parseEpg(it, profileId)
                } ?: throw IOException("Error downloading file: inputStream is null")
            } catch (ex: Exception) {
                throw ParseEpgFailedException(
                    "An error occurred when trying to parse the EPG from $url",
                    ex
                )
            }
        }

    private suspend fun downloadFile(url: String): InputStream? = withContext(dispatcher) {
        try {
            val request = Request.Builder().url(url).build()
            val response = httpClient.newCall(request).execute()
            if (!response.isSuccessful) {
                throw IOException("Error downloading: ${response.code}")
            }
            val inputStream = response.body?.byteStream()
            if (url.endsWith(".gz")) {
                GZIPInputStream(inputStream)
            } else {
                inputStream
            }
        } catch (e: IOException) {
            Log.e(TAG, "An error occurred while downloading the EPG file: ${e.message}")
            null
        }
    }

    private suspend fun parseEpg(inputStream: InputStream, profileId: String): List<EpgDataBO> = withContext(dispatcher) {
        val parser = createXmlParser(inputStream)
        val channelsMap = mutableMapOf<String, Pair<String, MutableList<ProgrammeDataBO>>>()
        val dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss")
        var eventType = parser.eventType

        while (eventType != XmlPullParser.END_DOCUMENT) {
            when (eventType) {
                XmlPullParser.START_TAG -> {
                    when (parser.name) {
                        CHANNEL_ENTRY_NAME -> {
                            parseChannel(parser)?.let { (id, displayName) ->
                                channelsMap[id] = Pair(displayName, mutableListOf())
                            }
                        }
                        PROGRAMME_ENTRY_NAME -> {
                            parseProgramme(parser, dateTimeFormatter)?.let { programme ->
                                channelsMap[programme.channelId]?.second?.add(programme)
                            }
                        }
                    }
                }
            }
            eventType = parser.next()
        }

        // Transform the channelsMap into a List<EpgDataBO>
        val epgDataList = channelsMap.map { (channelId, pair) ->
            EpgDataBO(
                channelId = channelId,
                displayName = pair.first,
                profileId = profileId,
                programmeList = pair.second
            )
        }
        epgDataList
    }

    private fun parseProgramme(
        parser: XmlPullParser,
        dateTimeFormatter: DateTimeFormatter
    ): ProgrammeDataBO? = with(parser) {
        val channelId = getAttributeValue(null, PROGRAMME_CHANNEL_ATTR)
        val start = getAttributeValue(null, PROGRAMME_START_ATTR)?.trim()?.substring(0, 14)
        val stop = getAttributeValue(null, PROGRAMME_STOP_ATTR)?.trim()?.substring(0, 14)
        var title: String? = null
        val id = UUID.randomUUID().toString()
        // Iterate through the tags within <programme>
        while (nextTag() != XmlPullParser.END_TAG) {
            if (name == "title") {
                title = nextText()
            } else {
                nextText() // Skip other tags
            }
        }

        return if (channelId != null && start != null && stop != null) {
            ProgrammeDataBO(
                id = id,
                channelId = channelId,
                title = title ?: "Unknown Title", // Default to "Unknown Title" if missing
                startTime = LocalDateTime.parse(start, dateTimeFormatter),
                endTime = LocalDateTime.parse(stop, dateTimeFormatter)
            )
        } else {
            Log.w(TAG, "Missing required data for programme: channelId=$channelId, start=$start, stop=$stop")
            null
        }
    }

    private fun createXmlParser(inputStream: InputStream): XmlPullParser {
        val factory = XmlPullParserFactory.newInstance()
        val parser = factory.newPullParser()
        parser.setInput(inputStream, Charsets.UTF_8.name())
        return parser
    }

    private fun parseChannel(parser: XmlPullParser): Pair<String, String>? = with(parser) {
        val channelId = getAttributeValue(null, "id")
        var displayName: String? = null

        // Process tags within <channel>
        while (nextTag() != XmlPullParser.END_TAG) {
            if (name == "display-name") {
                displayName = nextText()
            } else {
                nextText() // Skip other tags
            }
        }

        return if (channelId != null && displayName != null) {
            Pair(channelId, displayName)
        } else {
            Log.w(TAG, "Missing required data for channel: id=$channelId, displayName=$displayName")
            null
        }
    }
}