package com.dreamsoftware.nimbustv.data.repository.impl

import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.exception.DatabaseException
import com.dreamsoftware.nimbustv.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.nimbustv.domain.exception.GetChannelByIdException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.SaveChannelsException
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.SaveChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import kotlinx.coroutines.CoroutineDispatcher

internal class ChannelsRepositoryImpl(
    private val channelLocalDataSource: IChannelLocalDataSource,
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
}