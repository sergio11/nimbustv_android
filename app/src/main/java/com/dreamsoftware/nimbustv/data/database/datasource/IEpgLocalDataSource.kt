package com.dreamsoftware.nimbustv.data.database.datasource

import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.ISupportLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.EpgEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException

interface IEpgLocalDataSource: ISupportLocalDataSource<EpgEntity, String> {

    @Throws(AccessDatabaseException::class)
    suspend fun findAllByProfileId(profileId: String): List<EpgEntity>

    @Throws(AccessDatabaseException::class)
    suspend fun deleteAllByProfileId(profileId: String)
}