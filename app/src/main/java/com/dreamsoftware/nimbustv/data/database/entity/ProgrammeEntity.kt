package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.time.LocalDateTime

@TableName("programmes")
@Entity(
    tableName = "programmes",
    foreignKeys = [ForeignKey(
        entity = ChannelEpgEntity::class,
        parentColumns = ["id"],
        childColumns = ["channel_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["channel_id"])]
)
data class ProgrammeEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo(name = "channel_id")
    val channelId: String,
    val title: String,
    @ColumnInfo(name = "start_time")
    val startTime: LocalDateTime,
    @ColumnInfo(name = "end_time")
    val endTime: LocalDateTime
): IEntity<String>