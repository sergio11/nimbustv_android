package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ProgrammeEntity

@Dao
abstract class ProgrammeDao: SupportDaoImpl<ProgrammeEntity, String>(ProgrammeEntity::class)