package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ReminderDao
import com.dreamsoftware.nimbustv.data.database.datasource.IReminderLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ReminderEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import kotlinx.coroutines.CoroutineDispatcher

internal class ReminderLocalDataSourceImpl(
    private val reminderDao: ReminderDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<ReminderDao, ReminderEntity, String>(reminderDao, dispatcher), IReminderLocalDataSource {

    @Throws(AccessDatabaseException::class)
    override suspend fun findAllByProfileId(profileId: String): List<ReminderEntity> = safeExecute {
        reminderDao.findAllByProfileId(profileId)
    }
}