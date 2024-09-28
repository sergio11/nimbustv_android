package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "channels")
@Serializable
data class ChannelEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val title: String?,
    val url: String,
    val icon: String?,
    val category: String?,
)
