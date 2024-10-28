package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ProgrammeDao
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelScheduleEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import kotlinx.coroutines.CoroutineDispatcher

internal class ChannelScheduleEpgLocalDataSourceImpl(
    private val programmeDao: ProgrammeDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<ProgrammeDao, ChannelScheduleEntity, String>(programmeDao, dispatcher), IChannelScheduleEpgLocalDataSource {

    @Throws(AccessDatabaseException::class)
    override suspend fun findAllByEpgId(epgId: String): List<ChannelScheduleEntity> = safeExecute {
        programmeDao.findAllByEpgId(epgId)
    }
}