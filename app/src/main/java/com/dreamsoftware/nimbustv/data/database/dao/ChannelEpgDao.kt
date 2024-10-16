package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity

@Dao
abstract class ChannelEpgDao: SupportDaoImpl<ChannelEpgEntity, String>(ChannelEpgEntity::class)