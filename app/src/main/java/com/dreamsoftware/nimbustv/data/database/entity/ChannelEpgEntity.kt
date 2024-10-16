package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@TableName("channels_epg")
@Entity(tableName = "channels_epg")
data class ChannelEpgEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo(name = "display_name")
    val displayName: String
): IEntity<String>