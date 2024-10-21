package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.AddToFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.DeleteChannelByIdException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelByIdException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistAndCategoryException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.GetFavoriteChannelsByProfileIdException
import com.dreamsoftware.nimbustv.domain.exception.RemoveFromFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.SaveChannelsException
import com.dreamsoftware.nimbustv.domain.exception.VerifyFavoriteChannelException
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.SaveChannelBO

interface IChannelRepository {

    @Throws(SaveChannelsException::class)
    suspend fun save(channels: List<SaveChannelBO>): List<ChannelBO>

    @Throws(GetChannelByIdException::class)
    suspend fun findById(id: String): ChannelBO

    @Throws(GetChannelsByPlaylistException::class)
    suspend fun findAllByPlaylistId(playlistId: String): List<ChannelBO>

    @Throws(GetChannelsByPlaylistAndCategoryException::class)
    suspend fun findAllByPlaylistIdAndCategory(playlistId: String, category: String): List<ChannelBO>

    @Throws(DeleteChannelByIdException::class)
    suspend fun deleteById(id: String)

    @Throws(AddToFavoritesException::class)
    suspend fun addToFavorites(channelId: String, profileId: String)

    @Throws(RemoveFromFavoritesException::class)
    suspend fun removeFromFavorites(channelId: String, profileId: String)

    @Throws(VerifyFavoriteChannelException::class)
    suspend fun isFavoriteChannel(channelId: String, profileId: String): Boolean

    @Throws(GetFavoriteChannelsByProfileIdException::class)
    suspend fun findAllFavoriteChannelsByProfileId(profileId: String): List<ChannelBO>
}