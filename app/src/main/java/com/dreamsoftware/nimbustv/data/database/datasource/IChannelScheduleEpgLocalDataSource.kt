package com.dreamsoftware.nimbustv.data.database.datasource

import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.ISupportLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException

interface IChannelScheduleEpgLocalDataSource: ISupportLocalDataSource<ChannelScheduleEntity, String> {

    @Throws(AccessDatabaseException::class)
    suspend fun findAllByEpgId(epgId: String): List<ChannelScheduleEntity>
}