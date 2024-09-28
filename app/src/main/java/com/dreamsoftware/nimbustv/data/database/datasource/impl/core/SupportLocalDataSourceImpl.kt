package com.dreamsoftware.nimbustv.data.database.datasource.impl.core

import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.IEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import com.dreamsoftware.nimbustv.data.database.exception.DatabaseException
import com.dreamsoftware.nimbustv.data.database.exception.RecordNotFoundException
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext

abstract class SupportLocalDataSourceImpl<D : SupportDaoImpl<E>, E : IEntity>(
    private val dao: D,
    private val dispatcher: CoroutineDispatcher
): ISupportLocalDataSource<E> {

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun insert(entity: E): E = safeExecute {
        with(dao) {
            insert(entity).let {
                getById(entity.id) ?: throw RecordNotFoundException("record not found")
            }
        }
    }

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun update(entity: E) = safeExecute {
        dao.update(entity)
    }

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun delete(id: Long): Unit = safeExecute {
        with(dao) {
            getById(id)?.also { delete(it) } ?: throw RecordNotFoundException("record not found")
        }
    }

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun findAll(): List<E> = safeExecute {
        dao.getAll().takeIf { it.isNotEmpty() }
            ?: throw RecordNotFoundException("No records were found")
    }

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    override suspend fun findById(id: Long): E = safeExecute {
        dao.getById(id) ?: throw RecordNotFoundException("record not found")
    }

    @Throws(AccessDatabaseException::class)
    override suspend fun deleteAll() = safeExecute {
        dao.deleteAll()
    }

    protected suspend fun <T> safeExecute(block: suspend () -> T): T = withContext(dispatcher) {
        try {
            block()
        } catch (ex: DatabaseException) {
            throw ex
        } catch (ex: Exception) {
            throw AccessDatabaseException(cause = ex)
        }
    }
}