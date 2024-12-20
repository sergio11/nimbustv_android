package com.dreamsoftware.nimbustv.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import com.dreamsoftware.nimbustv.data.database.dao.core.SupportDaoImpl
import com.dreamsoftware.nimbustv.data.database.entity.ReminderEntity

@Dao
abstract class ReminderDao: SupportDaoImpl<ReminderEntity, String>(ReminderEntity::class) {

    @Query(value = "SELECT * FROM reminders WHERE profile_id = :profileId")
    abstract suspend fun findAllByProfileId(profileId: String): List<ReminderEntity>

    @Query(value = "SELECT * FROM reminders WHERE schedule_id = :scheduleId")
    abstract suspend fun findByScheduleId(scheduleId: String): ReminderEntity

    @Query("SELECT COUNT(*) FROM reminders WHERE schedule_id = :scheduleId")
    abstract suspend fun countByScheduleId(scheduleId: String): Long
}