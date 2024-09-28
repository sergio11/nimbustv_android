package com.dreamsoftware.nimbustv.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.dreamsoftware.nimbustv.data.database.dao.ChannelDao
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity

@Database(
    entities = [ChannelEntity::class],
    version = 1
)
abstract class NimbusTvDatabase : RoomDatabase() {
    abstract fun channelDao(): ChannelDao
}