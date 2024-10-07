package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ChannelDao
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import kotlinx.coroutines.CoroutineDispatcher

internal class ChannelLocalDataSourceImpl(
    private val channelDao: ChannelDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<ChannelDao, ChannelEntity, String>(channelDao, dispatcher), IChannelLocalDataSource {

    @Throws(AccessDatabaseException::class)
    override suspend fun findAllByPlaylistId(playlistId: String): List<ChannelEntity> = safeExecute {
        channelDao.findAllByPlaylistId(playlistId)
    }
}