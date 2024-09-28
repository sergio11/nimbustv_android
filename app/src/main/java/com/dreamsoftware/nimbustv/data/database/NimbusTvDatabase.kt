package com.dreamsoftware.nimbustv.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dreamsoftware.nimbustv.data.database.adapter.Adapters
import com.dreamsoftware.nimbustv.data.database.dao.ChannelDao
import com.dreamsoftware.nimbustv.data.database.dao.PlayListDao
import com.dreamsoftware.nimbustv.data.database.dao.ProfileDao
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.entity.PlayListM3UEntity
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity

@Database(
    entities = [ChannelEntity::class, PlayListM3UEntity::class, ProfileEntity::class],
    version = 1
)
@TypeConverters(Adapters::class)
abstract class NimbusTvDatabase : RoomDatabase() {
    abstract fun channelDao(): ChannelDao
    abstract fun profileDao(): ProfileDao
    abstract fun playListM3UDao(): PlayListDao
}