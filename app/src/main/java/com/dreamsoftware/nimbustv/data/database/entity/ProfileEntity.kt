package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,
    val alias: String,
    val isSecured: Boolean,
    val avatarType: String
): IEntity