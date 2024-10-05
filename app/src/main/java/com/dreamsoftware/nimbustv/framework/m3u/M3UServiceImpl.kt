package com.dreamsoftware.nimbustv.framework.m3u

import com.dreamsoftware.nimbustv.domain.exception.ParsePlayListFailedException
import com.dreamsoftware.nimbustv.domain.model.M3uEntryBO
import com.dreamsoftware.nimbustv.domain.service.IM3UService
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import net.bjoernpetersen.m3u.M3uParser
import net.bjoernpetersen.m3u.model.M3uEntry
import java.net.URL

internal class M3UServiceImpl(
    private val m3uEntryMapper: IOneSideMapper<M3uEntry, M3uEntryBO>,
    private val dispatcher: CoroutineDispatcher
): IM3UService {

    @Throws(ParsePlayListFailedException::class)
    override suspend fun parsePlaylist(playlistUrl: String): List<M3uEntryBO> = withContext(dispatcher) {
        try {
            M3uParser.parse(URL(playlistUrl).openStream().reader()).also {
                if (it.isEmpty()) {
                    throw Exception("Playlist does not contain any entry")
                }
            }.map(m3uEntryMapper::mapInToOut)
        } catch (ex: Exception) {
            throw ParsePlayListFailedException("An error occurred when trying to parse the playlist $playlistUrl", ex)
        }
    }
}