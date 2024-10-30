package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.EpgEntity

@Dao
abstract class EpgDao: SupportDaoImpl<EpgEntity, String>(EpgEntity::class) {

    @Query(value = "SELECT * FROM epg WHERE profile_id = :profileId")
    abstract suspend fun findAllByProfileId(profileId: String): List<EpgEntity>

    @Query(value = "DELETE FROM epg WHERE profile_id = :profileId")
    abstract suspend fun deleteAllByProfileId(profileId: String)

    @Query("UPDATE epg SET last_updated_at = CURRENT_TIMESTAMP WHERE id = :epgId")
    abstract suspend fun updateLastUpdatedAt(epgId: String)
}