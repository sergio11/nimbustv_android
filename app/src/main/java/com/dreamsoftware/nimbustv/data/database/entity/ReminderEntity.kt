package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@TableName("reminders")
@Entity(
    tableName = "reminders",
    foreignKeys = [
        ForeignKey(
            entity = ProfileEntity::class,
            parentColumns = ["id"],
            childColumns = ["profile_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = ChannelScheduleEntity::class,
            parentColumns = ["id"],
            childColumns = ["schedule_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [Index(value = ["profile_id"])]
)
data class ReminderEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),
    @ColumnInfo(name = "profile_id")
    val profileId: String,
    @ColumnInfo(name = "schedule_id")
    val scheduleId: String
) : IEntity<String>