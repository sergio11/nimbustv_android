package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.EpgDao
import com.dreamsoftware.nimbustv.data.database.datasource.IEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.EpgEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import kotlinx.coroutines.CoroutineDispatcher
import java.util.Date

internal class EpgLocalDataSourceImpl(
    private val epgDao: EpgDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<EpgDao, EpgEntity, String>(epgDao, dispatcher),
    IEpgLocalDataSource {

    @Throws(AccessDatabaseException::class)
    override suspend fun findAllByProfileId(profileId: String): List<EpgEntity> = safeExecute {
        epgDao.findAllByProfileId(profileId)
    }

    @Throws(AccessDatabaseException::class)
    override suspend fun deleteAllByProfileId(profileId: String): Unit = safeExecute {
        epgDao.deleteAllByProfileId(profileId)
    }

    @Throws(AccessDatabaseException::class)
    override suspend fun updateLastUpdatedAt(epgId: String) = safeExecute {
        epgDao.updateLastUpdatedAt(epgId, Date())
    }
}