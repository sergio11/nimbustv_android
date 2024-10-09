package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum

@TableName("channels")
@Entity(
    tableName = "channels",
    foreignKeys = [ForeignKey(
        entity = PlayListEntity::class,
        parentColumns = ["id"],
        childColumns = ["playlist_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["playlist_id"])]
)
data class ChannelEntity(
    @PrimaryKey
    override val id: String,
    val title: String?,
    val url: String,
    val icon: String?,
    val category: String?,
    @ColumnInfo(name = "manifest_type")
    val manifestType: String?,
    @ColumnInfo(name = "license_type")
    val licenseType: String?,
    @ColumnInfo(name = "license_key")
    val licenseKey: String?,
    @ColumnInfo(name = "stream_type")
    val streamType: StreamTypeEnum,
    @ColumnInfo(name = "playlist_id")
    val playlistId: String
): IEntity<String>
