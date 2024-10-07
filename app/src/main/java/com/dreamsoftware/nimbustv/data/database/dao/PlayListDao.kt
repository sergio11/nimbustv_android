package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity

@Dao
abstract class PlayListDao: SupportDaoImpl<PlayListEntity, String>(PlayListEntity::class) {

    @Query(value = "SELECT * FROM playlists WHERE profile_id = :profileId")
    abstract suspend fun findAllByProfileId(profileId: String): List<PlayListEntity>
}