package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity

@Dao
abstract class PlayListDao: SupportDaoImpl<PlayListEntity, String>(PlayListEntity::class)