package com.dreamsoftware.nimbustv.data.repository.impl

import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IFavoriteChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.entity.FavoriteChannelEntity
import com.dreamsoftware.nimbustv.data.database.exception.DatabaseException
import com.dreamsoftware.nimbustv.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.nimbustv.domain.exception.AddToFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.DeleteChannelByIdException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelByIdException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistAndCategoryException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.GetFavoriteChannelsByProfileIdException
import com.dreamsoftware.nimbustv.domain.exception.RemoveFromFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.SaveChannelsException
import com.dreamsoftware.nimbustv.domain.exception.SearchChannelsException
import com.dreamsoftware.nimbustv.domain.exception.VerifyFavoriteChannelException
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.SaveChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import kotlinx.coroutines.CoroutineDispatcher

internal class ChannelsRepositoryImpl(
    private val channelLocalDataSource: IChannelLocalDataSource,
    private val favoriteChannelLocalDataSource: IFavoriteChannelLocalDataSource,
    private val saveChannelsMapper: IOneSideMapper<SaveChannelBO, ChannelEntity>,
    private val channelsMapper: IOneSideMapper<ChannelEntity, ChannelBO>,
    dispatcher: CoroutineDispatcher
) : SupportRepositoryImpl(dispatcher), IChannelRepository {

    @Throws(SaveChannelsException::class)
    override suspend fun save(channels: List<SaveChannelBO>): List<ChannelBO> = safeExecute {
        try {
            channelLocalDataSource
                .insert(saveChannelsMapper.mapInListToOutList(channels).toList())
                .let(channelsMapper::mapInListToOutList)
                .toList()
        } catch (ex: DatabaseException) {
            throw SaveChannelsException(
                "An error occurred when trying to save channels",
                ex
            )
        }
    }

    @Throws(GetChannelByIdException::class)
    override suspend fun findById(id: String): ChannelBO = safeExecute {
        try {
            channelLocalDataSource
                .findById(id)
                .let(channelsMapper::mapInToOut)
        } catch (ex: DatabaseException) {
            throw GetChannelByIdException(
                "An error occurred when trying to find a channel by id",
                ex
            )
        }
    }

    @Throws(GetChannelsByPlaylistException::class)
    override suspend fun findAllByPlaylistId(playlistId: String): List<ChannelBO> = safeExecute {
        try {
            channelLocalDataSource
                .findAllByPlaylistId(playlistId)
                .let(channelsMapper::mapInListToOutList)
                .toList()
        } catch (ex: DatabaseException) {
            throw GetChannelsByPlaylistException(
                "An error occurred when trying to find all channels by playlist id",
                ex
            )
        }
    }

    @Throws(GetChannelsByPlaylistAndCategoryException::class)
    override suspend fun findAllByPlaylistIdAndCategory(
        playlistId: String,
        category: String
    ): List<ChannelBO> = safeExecute {
        try {
            channelLocalDataSource
                .findAllByPlaylistIdAndCategory(playlistId, category)
                .let(channelsMapper::mapInListToOutList)
                .toList()
        } catch (ex: DatabaseException) {
            throw GetChannelsByPlaylistAndCategoryException(
                "An error occurred when trying to find all channels by playlist id and category",
                ex
            )
        }
    }

    @Throws(DeleteChannelByIdException::class)
    override suspend fun deleteById(id: String): Unit = safeExecute {
        try {
            channelLocalDataSource.delete(id)
        } catch (ex: DatabaseException) {
            throw DeleteChannelByIdException(
                "An error occurred when trying to delete channel by id",
                ex
            )
        }
    }

    @Throws(AddToFavoritesException::class)
    override suspend fun addToFavorites(channelId: String, profileId: String): Unit = safeExecute {
        try {
            favoriteChannelLocalDataSource.insert(
                FavoriteChannelEntity(
                    profileId = profileId,
                    channelId = channelId
                )
            )
        } catch (ex: DatabaseException) {
            throw AddToFavoritesException(
                "An error occurred when trying to add channel to favorite list",
                ex
            )
        }
    }

    @Throws(RemoveFromFavoritesException::class)
    override suspend fun removeFromFavorites(channelId: String, profileId: String): Unit = safeExecute {
        try {
            favoriteChannelLocalDataSource.deleteByChannelIdAndProfileId(channelId, profileId)
        } catch (ex: DatabaseException) {
            throw RemoveFromFavoritesException(
                "An error occurred when trying to remove channel from favorites",
                ex
            )
        }
    }

    @Throws(VerifyFavoriteChannelException::class)
    override suspend fun isFavoriteChannel(channelId: String, profileId: String): Boolean = safeExecute {
        try {
            favoriteChannelLocalDataSource.isFavoriteChannel(channelId, profileId)
        } catch (ex: DatabaseException) {
            throw VerifyFavoriteChannelException(
                "An error occurred when trying to verify favorite channel",
                ex
            )
        }
    }

    @Throws(GetFavoriteChannelsByProfileIdException::class)
    override suspend fun findAllFavoriteChannelsByProfileId(profileId: String): List<ChannelBO> = safeExecute {
        try {
            favoriteChannelLocalDataSource
                .findFavoriteChannelsByProfileId(profileId)
                .let(channelsMapper::mapInListToOutList)
                .toList()
        } catch (ex: DatabaseException) {
            throw GetFavoriteChannelsByProfileIdException(
                "An error occurred when trying to get favorite channels",
                ex
            )
        }
    }

    @Throws(SearchChannelsException::class)
    override suspend fun findAllByProfileIdAndTerm(
        profileId: String,
        term: String
    ): List<ChannelBO> = safeExecute {
        try {
            channelLocalDataSource
                .findAllByProfileIdAndTerm(profileId, term)
                .let(channelsMapper::mapInListToOutList)
                .toList()
        } catch (ex: DatabaseException) {
            throw GetFavoriteChannelsByProfileIdException(
                "An error occurred when trying to search channels",
                ex
            )
        }
    }
}