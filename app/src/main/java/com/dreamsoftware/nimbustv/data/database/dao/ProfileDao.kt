package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity

@Dao
abstract class ProfileDao: SupportDaoImpl<ProfileEntity>(ProfileEntity::class)