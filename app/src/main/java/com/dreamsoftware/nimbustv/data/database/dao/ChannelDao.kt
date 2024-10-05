package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity

@Dao
abstract class ChannelDao: SupportDaoImpl<ChannelEntity, Long>(ChannelEntity::class)