package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import java.util.Date

@TableName("profiles")
@Entity(
    tableName = "playlists",
    foreignKeys = [ForeignKey(
        entity = ProfileEntity::class,
        parentColumns = ["id"],
        childColumns = ["profileId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["profileId"])]
)
data class PlayListM3UEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,
    val alias: String,
    val creationDate: Date = Date(),
    val m3uUrl: String,
    val profileId: Long
): IEntity