package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ChannelEpgDao
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import kotlinx.coroutines.CoroutineDispatcher

internal class ChannelEpgLocalDataSourceImpl(
    private val channelEpgDao: ChannelEpgDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<ChannelEpgDao, ChannelEpgEntity, String>(channelEpgDao, dispatcher), IChannelEpgLocalDataSource {

    @Throws(AccessDatabaseException::class)
    override suspend fun deleteAllByProfileId(profileId: String): Unit = safeExecute {
        channelEpgDao.deleteAllByProfileId(profileId)
    }
}