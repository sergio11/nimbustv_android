package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ChannelDao
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import com.dreamsoftware.nimbustv.data.database.exception.ChannelRecordNotFoundException
import kotlinx.coroutines.CoroutineDispatcher

internal class ChannelLocalDataSourceImpl(
    private val channelDao: ChannelDao,
    dispatcher: CoroutineDispatcher
): SupportDataSourceImpl(dispatcher), IChannelLocalDataSource {

    @Throws(ChannelRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun insert(entity: ChannelEntity): ChannelEntity = safeExecute {
        with(channelDao) {
            insert(entity).let { getById(entity.id) ?: throw ChannelRecordNotFoundException("channel not found") }
        }
    }

    @Throws(ChannelRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun update(entity: ChannelEntity) = safeExecute {
        channelDao.update(entity)
    }

    @Throws(ChannelRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun delete(id: Long): Unit = safeExecute {
        with(channelDao) {
            getById(id)?.also { delete(it) } ?: throw ChannelRecordNotFoundException("channel not found")
        }
    }

    @Throws(ChannelRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun findAll(): List<ChannelEntity> = safeExecute {
        channelDao.getAll().takeIf { it.isNotEmpty() } ?: throw ChannelRecordNotFoundException("No channels were found")
    }

    @Throws(ChannelRecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun findById(id: Long): ChannelEntity = safeExecute {
        channelDao.getById(id) ?: throw ChannelRecordNotFoundException("channel not found")
    }

    @Throws(AccessDatabaseException::class)
    override suspend fun deleteAll() = safeExecute {
        channelDao.deleteAll()
    }
}