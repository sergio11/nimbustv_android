package com.dreamsoftware.nimbustv.data.database.datasource.impl.core

import com.dreamsoftware.nimbustv.data.database.entity.IEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import com.dreamsoftware.nimbustv.data.database.exception.RecordNotFoundException

interface ISupportLocalDataSource<E: IEntity<K>, K> {

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun insert(entity: E): E

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun insert(entityList: List<E>): List<E>

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun update(entity: E): E

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun delete(id: K): Int

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun findAll(): List<E>

    @Throws(AccessDatabaseException::class)
    suspend fun count(): Long

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun findById(id: K): E

    @Throws(AccessDatabaseException::class)
    suspend fun deleteAll(): Long
}