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
        entity = ProfileEntity::class,
        parentColumns = ["id"],
        childColumns = ["profile_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["profile_id"])]
)
data class ChannelEpgEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo(name = "display_name")
    val displayName: String,
    @ColumnInfo(name = "profile_id")
    val profileId: String
): IEntity<String>