package com.dreamsoftware.nimbustv.data.database.datasource

import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.ISupportLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ReminderEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException

interface IReminderLocalDataSource: ISupportLocalDataSource<ReminderEntity, String> {

    @Throws(AccessDatabaseException::class)
    suspend fun findAllByProfileId(profileId: String): List<ReminderEntity>
}