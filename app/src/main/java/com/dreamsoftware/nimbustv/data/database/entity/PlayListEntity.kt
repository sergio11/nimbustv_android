package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@TableName("playlists")
@Entity(
    tableName = "playlists",
    foreignKeys = [ForeignKey(
        entity = ProfileEntity::class,
        parentColumns = ["id"],
        childColumns = ["profile_id"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["profile_id"])]
)
data class PlayListEntity(
    @PrimaryKey
    override val id: String,
    val alias: String,
    @ColumnInfo(name = "created_at")
    val createdAt: Date = Date(),
    val url: String,
    @ColumnInfo(name = "profile_id")
    val profileId: String
): IEntity<String>