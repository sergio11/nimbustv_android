package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.PlayListM3UEntity

@Dao
abstract class PlayListM3UDao: SupportDaoImpl<PlayListM3UEntity>(PlayListM3UEntity::class)