package com.dreamsoftware.nimbustv.data.database.dao.core

import androidx.sqlite.db.SimpleSQLiteQuery
import com.dreamsoftware.nimbustv.data.database.entity.IEntity
import com.dreamsoftware.nimbustv.data.database.entity.TableName
import kotlin.reflect.KClass

abstract class SupportDaoImpl<E: IEntity<K>, K: Any>(private val entityClass: KClass<*>) : ISupportDAO<E> {

    suspend fun count(): Long = _count(SimpleSQLiteQuery("SELECT COUNT(*) FROM ${getTableName(entityClass)}"))

    suspend fun deleteAll(): Long = _deleteAll(SimpleSQLiteQuery("DELETE FROM ${getTableName(entityClass)}"))

    suspend fun getAll(): List<E> =
        _getAll(SimpleSQLiteQuery("SELECT * FROM ${getTableName(entityClass)} ORDER BY id ASC"))

    suspend fun getById(id: Any): E? =
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