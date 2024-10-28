package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity

@Dao
abstract class ProgrammeDao: SupportDaoImpl<ChannelScheduleEntity, String>(ChannelScheduleEntity::class) {

    @Query(value = "SELECT * FROM programmes WHERE epg_id = :epgId")
    abstract suspend fun findAllByEpgId(epgId: String): List<ChannelScheduleEntity>
}