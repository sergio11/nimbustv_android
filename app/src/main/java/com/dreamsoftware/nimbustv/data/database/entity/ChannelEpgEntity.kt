package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@TableName("channels_epg")
@Entity(
    tableName = "channels_epg",
    foreignKeys = [ForeignKey(
        entity = EpgEntity::class,
        parentColumns = ["id"],
        childColumns = ["epg_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["epg_id"])]
)
data class ChannelEpgEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "epg_id")
    val epgId: String
): IEntity<String>