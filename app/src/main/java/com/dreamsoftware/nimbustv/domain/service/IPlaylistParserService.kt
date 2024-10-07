package com.dreamsoftware.nimbustv.domain.service

import com.dreamsoftware.nimbustv.domain.exception.ParsePlayListFailedException
import com.dreamsoftware.nimbustv.domain.model.M3uEntryBO

interface IPlaylistParserService {

    @Throws(ParsePlayListFailedException::class)
    suspend fun parsePlaylist(playlistUrl: String): List<M3uEntryBO>
}