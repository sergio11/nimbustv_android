package com.dreamsoftware.nimbustv.data.database.datasource

import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.ISupportLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException

interface IChannelEpgLocalDataSource: ISupportLocalDataSource<ChannelEpgEntity, String> {

    @Throws(AccessDatabaseException::class)
    suspend fun findAllByEpgId(epgId: String): List<ChannelEpgEntity>

    @Throws(AccessDatabaseException::class)
    suspend fun deleteAllByEpgId(epgId: String)
}