package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity

@Dao
abstract class ChannelDao: SupportDaoImpl<ChannelEntity, String>(ChannelEntity::class) {

    @Query(value = "SELECT * FROM channels WHERE playlist_id = :playlist")
    abstract suspend fun findAllByPlaylistId(playlist: String): List<ChannelEntity>
}