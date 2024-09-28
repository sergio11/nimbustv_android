package com.dreamsoftware.nimbustv.data.database.dao.core

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.RawQuery
import androidx.room.Update
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface ISupportDAO<T> {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: T)

    @Update
    suspend fun update(entity: T)

    @Delete
    suspend fun delete(entity: T): Int

    @RawQuery
    suspend fun _deleteAll(query: SupportSQLiteQuery): Long

    @RawQuery
    suspend fun _count(query: SupportSQLiteQuery): Long

    @RawQuery
    suspend fun _getAll(query: SupportSQLiteQuery): List<T>

    @RawQuery
    suspend fun _getById(query: SupportSQLiteQuery): T?
}
