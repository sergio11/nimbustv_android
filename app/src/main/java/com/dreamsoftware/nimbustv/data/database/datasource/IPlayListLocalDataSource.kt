package com.dreamsoftware.nimbustv.data.database.datasource

import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.ISupportLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException

interface IPlayListLocalDataSource: ISupportLocalDataSource<PlayListEntity, String> {

    @Throws(AccessDatabaseException::class)
    suspend fun findAllByProfileId(profileId: String): List<PlayListEntity>
}