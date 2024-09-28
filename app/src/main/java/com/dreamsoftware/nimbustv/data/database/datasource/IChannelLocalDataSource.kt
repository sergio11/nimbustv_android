package com.dreamsoftware.nimbustv.data.database.datasource

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import com.dreamsoftware.nimbustv.data.database.exception.ChannelRecordNotFoundException

interface IChannelLocalDataSource {

    @Throws(ChannelRecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun insert(entity: ChannelEntity): ChannelEntity

    @Throws(ChannelRecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun update(entity: ChannelEntity)

    @Throws(ChannelRecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun delete(id: Long)

    @Throws(AccessDatabaseException::class)
    suspend fun findAll(): List<ChannelEntity>

    @Throws(ChannelRecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun findById(id: Long): ChannelEntity

    @Throws(AccessDatabaseException::class)
    suspend fun deleteAll()
}