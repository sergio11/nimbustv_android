package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@TableName("profiles")
@Entity(
    tableName = "channels",
    foreignKeys = [ForeignKey(
        entity = PlayListM3UEntity::class,
        parentColumns = ["id"],
        childColumns = ["playlistId"],
        onDelete = ForeignKey.CASCADE
    )],
    indices = [Index(value = ["playlistId"])]
)
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,
    val title: String?,
    val url: String,
    val icon: String?,
    val category: String?,
    val playlistId: Long
): IEntity<Long>
