package com.dreamsoftware.nimbustv.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.dreamsoftware.nimbustv.data.database.adapter.Adapters
import com.dreamsoftware.nimbustv.data.database.dao.ChannelDao
import com.dreamsoftware.nimbustv.data.database.dao.ChannelEpgDao
import com.dreamsoftware.nimbustv.data.database.dao.EpgDao
import com.dreamsoftware.nimbustv.data.database.dao.FavoriteChannelDao
import com.dreamsoftware.nimbustv.data.database.dao.PlayListDao
import com.dreamsoftware.nimbustv.data.database.dao.ProfileDao
import com.dreamsoftware.nimbustv.data.database.dao.ProgrammeDao
import com.dreamsoftware.nimbustv.data.database.dao.ReminderDao
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.EpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.FavoriteChannelEntity
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity
import com.dreamsoftware.nimbustv.data.database.entity.ReminderEntity

@Database(
    entities = [
        ChannelEntity::class,
        PlayListEntity::class,
        ProfileEntity::class,
        EpgEntity::class,
        ChannelEpgEntity::class,
        ChannelScheduleEntity::class,
        FavoriteChannelEntity::class,
        ReminderEntity::class
    ],
    version = 1
)
@TypeConverters(Adapters::class)
abstract class NimbusTvDatabase : RoomDatabase() {
    abstract fun channelDao(): ChannelDao
    abstract fun epgDao(): EpgDao
    abstract fun channelEpgDao(): ChannelEpgDao
    abstract fun profileDao(): ProfileDao
    abstract fun programmeDao(): ProgrammeDao
    abstract fun playListM3UDao(): PlayListDao
    abstract fun favoriteChannelDao(): FavoriteChannelDao
    abstract fun reminderDao(): ReminderDao
}