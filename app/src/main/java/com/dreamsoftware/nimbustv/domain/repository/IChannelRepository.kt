package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.SaveChannelsException
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.SaveChannelBO

interface IChannelRepository {

    @Throws(SaveChannelsException::class)
    suspend fun save(channels: List<SaveChannelBO>): List<ChannelBO>

    @Throws(GetChannelsByPlaylistException::class)
    suspend fun findAllByPlaylistId(playlistId: String): List<ChannelBO>
}