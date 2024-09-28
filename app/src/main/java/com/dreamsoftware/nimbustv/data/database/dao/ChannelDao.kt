package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity

@Dao
interface ChannelDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insert(entity: ChannelEntity)

    @Update
    suspend fun update(entity: ChannelEntity)

    @Delete
    suspend fun delete(entity: ChannelEntity)

    @Query("SELECT * FROM channels ORDER BY id ASC")
    fun getAll(): List<ChannelEntity>

    @Query("SELECT * FROM `channels` WHERE id = :id")
    fun getById(id: Long): ChannelEntity?

    @Query("DELETE FROM channels")
    fun deleteAll()
}