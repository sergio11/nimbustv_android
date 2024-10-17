package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.entity.FavoriteChannelEntity

@Dao
abstract class FavoriteChannelDao: SupportDaoImpl<FavoriteChannelEntity, Long>(FavoriteChannelEntity::class) {

    // Delete all favorite channels for a specific profile
    @Query("DELETE FROM favorite_channels WHERE profile_id = :profileId")
    abstract suspend fun deleteAllByProfileId(profileId: String)

    @Query("DELETE FROM favorite_channels WHERE channel_id = :channelId AND profile_id = :profileId")
    abstract suspend fun deleteByChannelIdAndProfileId(channelId: String, profileId: String)

    // Get favorite channels for a specific profile
    @Query("""
        SELECT c.*
        FROM channels c
        INNER JOIN favorite_channels fc ON c.id = fc.channel_id
        WHERE fc.profile_id = :profileId
    """)
    abstract suspend fun findFavoriteChannelsByProfileId(profileId: String): List<ChannelEntity>
}