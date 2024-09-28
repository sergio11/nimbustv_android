package com.dreamsoftware.nimbustv.data.database.datasource.impl.core

import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import com.dreamsoftware.nimbustv.data.database.exception.RecordNotFoundException

interface ISupportLocalDataSource<T> {

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun insert(entity: T): T

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun update(entity: T)

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun delete(id: Long)

    @Throws(AccessDatabaseException::class)
    suspend fun findAll(): List<T>

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun findById(id: Long): T

    @Throws(AccessDatabaseException::class)
    suspend fun deleteAll()
}