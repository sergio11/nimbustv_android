package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity

@Dao
abstract class ChannelEpgDao: SupportDaoImpl<ChannelEpgEntity, String>(ChannelEpgEntity::class) {

    @Query(value = "SELECT * FROM channels_epg WHERE epg_id = :epgId")
    abstract suspend fun findAllByEpgId(epgId: String): List<ChannelEpgEntity>
}