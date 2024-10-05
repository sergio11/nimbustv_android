package com.dreamsoftware.nimbustv.data.database.dao.core

import androidx.sqlite.db.SimpleSQLiteQuery
import com.dreamsoftware.nimbustv.data.database.entity.IEntity
import com.dreamsoftware.nimbustv.data.database.entity.TableName
import kotlin.reflect.KClass

abstract class SupportDaoImpl<T: IEntity>(private val entityClass: KClass<*>) : ISupportDAO<T> {

    suspend fun count(): Long = _count(SimpleSQLiteQuery("SELECT COUNT(*) FROM ${getTableName(entityClass)}"))

    suspend fun deleteAll(): Long = _deleteAll(SimpleSQLiteQuery("DELETE FROM ${getTableName(entityClass)}"))

    suspend fun getAll(): List<T> =
        _getAll(SimpleSQLiteQuery("SELECT * FROM ${getTableName(entityClass)} ORDER BY id ASC"))

    suspend fun getById(id: Long): T? =
        _getById(
            SimpleSQLiteQuery(
                "SELECT * FROM ${getTableName(entityClass)} WHERE id = ?",
                arrayOf(id)
            )
        )

    private fun getTableName(entityClass: KClass<*>): String {
        return entityClass.annotations
            .filterIsInstance<TableName>()
            .firstOrNull()?.value
            ?: throw IllegalArgumentException("Entity annotation is missing for ${entityClass.simpleName}")
    }
}