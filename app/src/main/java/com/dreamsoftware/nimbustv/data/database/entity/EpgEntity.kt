package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@TableName("epg")
@Entity(
    tableName = "epg",
    foreignKeys = [ForeignKey(
        entity = ProfileEntity::class,
        parentColumns = ["id"],
        childColumns = ["profile_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["profile_id"])]
)
data class EpgEntity(
    @PrimaryKey
    override val id: String,
    @ColumnInfo(name = "alias")
    val alias: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),
    @ColumnInfo(name = "last_updated_at")
    val lastUpdatedAt: Date = Date(),
    @ColumnInfo(name = "url")
    val url: String,
    @ColumnInfo(name = "channels_count")
    val channelsCount: Long,
    @ColumnInfo(name = "profile_id")
    val profileId: String
): IEntity<String>