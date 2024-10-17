package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.AddToFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelByIdException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.GetFavoriteChannelsByProfileIdException
import com.dreamsoftware.nimbustv.domain.exception.RemoveFromFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.SaveChannelsException
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.SaveChannelBO

interface IChannelRepository {

    @Throws(SaveChannelsException::class)
    suspend fun save(channels: List<SaveChannelBO>): List<ChannelBO>

    @Throws(GetChannelByIdException::class)
    suspend fun findById(id: String): ChannelBO

    @Throws(GetChannelsByPlaylistException::class)
    suspend fun findAllByPlaylistId(playlistId: String): List<ChannelBO>

    @Throws(AddToFavoritesException::class)
    suspend fun addToFavorites(channelId: String, profileId: String)

    @Throws(RemoveFromFavoritesException::class)
    suspend fun removeFromFavorites(channelId: String, profileId: String)

    @Throws(GetFavoriteChannelsByProfileIdException::class)
    suspend fun findAllFavoriteChannelsByProfileId(profileId: String): List<ChannelBO>
}