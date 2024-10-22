package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity

@Dao
abstract class ChannelDao: SupportDaoImpl<ChannelEntity, String>(ChannelEntity::class) {

    @Query(value = "SELECT * FROM channels WHERE playlist_id = :playlist")
    abstract suspend fun findAllByPlaylistId(playlist: String): List<ChannelEntity>

    @Query(value = "SELECT * FROM channels WHERE playlist_id = :playlist AND category = :category")
    abstract suspend fun findAllByPlaylistIdAndCategory(playlist: String, category: String): List<ChannelEntity>

    @Query("SELECT c.* FROM channels c " +
            "JOIN playlists p ON c.playlist_id = p.id " +
            "WHERE p.profile_id = :profileId " +
            "AND LOWER(c.title) LIKE '%' || LOWER(:term) || '%'")
    abstract suspend fun findAllByProfileIdAndTerm(profileId: String, term: String): List<ChannelEntity>
}