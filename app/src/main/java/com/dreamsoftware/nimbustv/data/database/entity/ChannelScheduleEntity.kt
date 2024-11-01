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
    foreignKeys = [
        ForeignKey(
            entity = ChannelEpgEntity::class,
            parentColumns = ["id"],
            childColumns = ["channel_id"],
            onDelete = ForeignKey.CASCADE
        ),
        ForeignKey(
            entity = EpgEntity::class,
            parentColumns = ["id"],
            childColumns = ["epg_id"],
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index(value = ["channel_id"]),
        Index(value = ["epg_id"])
    ]
)
data class ChannelScheduleEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo(name = "channel_id")
    val channelId: String,
    @ColumnInfo(name = "title")
    val title: String,
    @ColumnInfo(name = "description")
    val description: String?,
    @ColumnInfo(name = "start_time")
    val startTime: LocalDateTime,
    @ColumnInfo(name = "end_time")
    val endTime: LocalDateTime,
    @ColumnInfo(name = "epg_id")
    val epgId: String
) : IEntity<String>