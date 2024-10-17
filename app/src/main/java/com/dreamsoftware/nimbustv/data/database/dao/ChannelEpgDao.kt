package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity

@Dao
abstract class ChannelEpgDao: SupportDaoImpl<ChannelEpgEntity, String>(ChannelEpgEntity::class) {

    @Query(value = "DELETE FROM channels_epg WHERE profile_id = :profileId")
    abstract suspend fun deleteAllByProfileId(profileId: String)
}