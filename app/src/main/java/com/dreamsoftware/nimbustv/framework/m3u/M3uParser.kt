package com.dreamsoftware.nimbustv.framework.m3u

import android.util.Log
import com.dreamsoftware.nimbustv.framework.m3u.model.M3uEntry
import com.dreamsoftware.nimbustv.framework.m3u.model.M3uMetadata
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import java.nio.charset.Charset
import java.nio.file.Files
import java.nio.file.Path
import java.time.Duration
import java.util.LinkedList
import kotlin.streams.asSequence
import com.dreamsoftware.nimbustv.framework.m3u.model.MediaLocation
import com.dreamsoftware.nimbustv.framework.m3u.model.MediaPath

// Enum class to represent Stream Types
enum class StreamType {
    ONLY_AUDIO,
    VIDEO
}

object M3uParser {
    private const val TAG = "M3U_PARSER"
    private const val COMMENT_START = '#'
    private const val EXTENDED_HEADER = "${COMMENT_START}EXTM3U"
    private const val SECONDS = 1
    private const val KEY_VALUE_PAIRS = 2
    private const val TITLE = 3
    private const val EXTENDED_INFO = """${COMMENT_START}EXTINF:([-]?\d+)(.*),(.+)"""
    private const val KODI_PROP = """#KODIPROP:([\w\-.]+)=(.+)""" // Regex for KODIPROP

    private val infoRegex = Regex(EXTENDED_INFO)
    private val kodiPropRegex = Regex(KODI_PROP)  // Regex for KODIPROP properties

    /**
     * Parses the specified file.
     *
     * @param m3uFile a path to an .m3u file
     * @param charset the file's encoding, defaults to UTF-8
     * @return a list of all contained entries in order
     * @throws IOException if file can't be read
     * @throws IllegalArgumentException if file is not a regular file
     */
    @Throws(IOException::class)
    @JvmStatic
    @JvmOverloads
    fun parse(m3uFile: Path, charset: Charset = Charsets.UTF_8): List<M3uEntry> {
        require(Files.isRegularFile(m3uFile)) { "$m3uFile is not a file" }
        return parse(Files.lines(m3uFile, charset).asSequence(), m3uFile.parent)
    }

    /**
     * Parses the [InputStream] from the specified reader.
     *
     * @param m3uContentReader a reader reading the content of an `.m3u` file
     * @param baseDir a base dir for resolving relative paths
     * @return a list of all parsed entries in order
     */
    @JvmStatic
    @JvmOverloads
    fun parse(m3uContentReader: InputStreamReader, baseDir: Path? = null): List<M3uEntry> {
        return m3uContentReader.buffered().useLines { parse(it, baseDir) }
    }

    /**
     * Parses the specified content of a `.m3u` file.
     *
     * @param m3uContent the content of a `.m3u` file
     * @param baseDir a base dir for resolving relative paths
     * @return a list of all parsed entries in order
     */
    @JvmStatic
    @JvmOverloads
    fun parse(m3uContent: String, baseDir: Path? = null): List<M3uEntry> {
        return parse(m3uContent.lineSequence(), baseDir)
    }

    /**
     * Recursively resolves all playlist files contained as entries in the given list.
     *
     * Note that unresolvable playlist file entries will be dropped.
     *
     * @param entries a list of playlist entries
     * @param charset the encoding to be used to read nested playlist files, defaults to UTF-8
     */
    @JvmStatic
    @JvmOverloads
    fun resolveNestedPlaylists(
        entries: List<M3uEntry>,
        charset: Charset = Charsets.UTF_8,
    ): List<M3uEntry> {
        return resolveRecursively(entries, charset)
    }

    private fun parse(lines: Sequence<String>, baseDir: Path?): List<M3uEntry> {
        val filtered = lines
            .filterNot { it.isBlank() }
            .map { it.trimEnd() }
            .dropWhile { it == EXTENDED_HEADER }
            .iterator()

        if (!filtered.hasNext()) return emptyList()

        val entries = LinkedList<M3uEntry>()
        var kodiProps = mutableMapOf<String, String>()
        var currentLine: String
        var match: MatchResult? = null

        while (filtered.hasNext()) {
            currentLine = filtered.next()

            // Capture #KODIPROP
            if (currentLine.startsWith("#KODIPROP")) {
                val kodiPropMatch = kodiPropRegex.matchEntire(currentLine)
                if (kodiPropMatch != null) {
                    val key = kodiPropMatch.groups[1]?.value ?: continue
                    val value = kodiPropMatch.groups[2]?.value ?: continue
                    kodiProps[key] = value  // Store KODI directive
                }
                continue
            }

            // Capture #EXTINF
            while (currentLine.startsWith(COMMENT_START)) {
                val newMatch = infoRegex.matchEntire(currentLine)
                if (newMatch != null) {
                    if (match != null) Log.d(TAG, "Ignoring info line: ${match.value}")
                    match = newMatch
                } else {
                    Log.d(TAG, "Ignoring comment line $currentLine")
                }

                if (filtered.hasNext()) {
                    currentLine = filtered.next()
                } else {
                    return entries
                }
            }

            // Process URI entry
            val entry = if (currentLine.startsWith(COMMENT_START)) {
                continue
            } else if (match == null) {
                parseSimple(currentLine, baseDir, kodiProps)
            } else {
                parseExtended(match, currentLine, baseDir, kodiProps)
            }

            match = null

            if (entry != null) {
                entries.add(entry)
            } else {
                Log.d(TAG, "Ignored line $currentLine")
            }

            kodiProps = mutableMapOf()  // Clear KODI props for next entry
        }

        return entries
    }

    private fun parseSimple(location: String, baseDir: Path?, kodiProps: Map<String, String>): M3uEntry? {
        val streamType = determineStreamType(location) // Determine the stream type based on the location

        return try {
            M3uEntry(MediaLocation(location, baseDir), kodiProps = kodiProps, streamType = streamType)
        } catch (e: IllegalArgumentException) {
            Log.d(TAG, "Could not parse as location: $location")
            null
        }
    }

    // Function to determine the stream type based on the location
    private fun determineStreamType(location: String): StreamType {
        return when {
            location.endsWith(".mpd") || location.endsWith(".m3u8") -> StreamType.ONLY_AUDIO
            location.contains("radio", ignoreCase = true) -> StreamType.ONLY_AUDIO
            else -> StreamType.VIDEO
        }
    }


    private fun parseExtended(infoMatch: MatchResult, location: String, baseDir: Path?, kodiProps: Map<String, String>): M3uEntry? {
        val mediaLocation = try {
            MediaLocation(location, baseDir)
        } catch (e: IllegalArgumentException) {
            Log.d(TAG, "Could not parse as location: $location")
            return null
        }

        val duration = infoMatch.groups[SECONDS]?.value?.toLong()
            ?.let { if (it < 0) null else it }
            ?.let { Duration.ofSeconds(it) }
        val metadata = parseMetadata(infoMatch.groups[KEY_VALUE_PAIRS]?.value)
        val title = infoMatch.groups[TITLE]?.value

        // Determine the stream type, default to VIDEO
        val streamType = determineStreamType(location, title, metadata)

        return M3uEntry(mediaLocation, duration, title, metadata, kodiProps, streamType)
    }

    private fun resolveRecursively(
        source: List<M3uEntry>,
        charset: Charset,
        result: MutableList<M3uEntry> = LinkedList(),
    ): List<M3uEntry> {
        for (entry in source) {
            val location = entry.location
            if (location is MediaPath && location.isPlaylistPath) {
                resolveNestedPlaylist(location.path, charset, result)
            } else {
                result.add(entry)
            }
        }
        return result
    }

    private fun resolveNestedPlaylist(
        path: Path,
        charset: Charset,
        result: MutableList<M3uEntry>,
    ) {
        if (!Files.isRegularFile(path)) {
            return
        }

        val parsed = try {
            parse(path, charset)
        } catch (e: IOException) {
            Log.d(TAG, "Could not parse nested playlist file: $path")
            return
        }

        resolveRecursively(parsed, charset, result)
    }

    /**
     * Determines the type of stream based on location, title, and metadata.
     *
     * @param location the URL or path of the entry
     * @param title the title of the entry
     * @param metadata the metadata of the entry
     * @return the type of the stream (StreamType.ONLY_AUDIO or StreamType.VIDEO)
     */
    private fun determineStreamType(location: String, title: String?, metadata: M3uMetadata): StreamType {
        // Default to VIDEO for most formats
        var streamType = StreamType.VIDEO

        // Check for specific conditions that indicate it is ONLY_AUDIO
        if (title?.contains("radio", ignoreCase = true) == true ||
            metadata.keys.any { it.contains("radio", ignoreCase = true) } ||
            location.endsWith(".mp3", ignoreCase = true) ||
            location.endsWith(".aac", ignoreCase = true) ||
            location.endsWith(".wav", ignoreCase = true) ||
            location.endsWith(".ogg", ignoreCase = true) ||
            location.endsWith(".m3u8", ignoreCase = true) ||
            location.endsWith(".mpd", ignoreCase = true)) {
            streamType = StreamType.ONLY_AUDIO
        }

        return streamType
    }

    private fun parseMetadata(keyValues: String?): M3uMetadata {
        if (keyValues == null) {
            return M3uMetadata.empty()
        }

        val keyValuePattern = Regex("""([\w-_.]+)="(.*?)"( )?""")
        val valueByKey = HashMap<String, String>()
        for (match in keyValuePattern.findAll(keyValues.trim())) {
            val key = match.groups[1]!!.value
            val value = match.groups[2]?.value?.ifBlank { null }
            if (value == null) {
                valueByKey[key] = ""
            } else {
                valueByKey[key] = value
            }
        }
        return M3uMetadata(valueByKey)
    }
}