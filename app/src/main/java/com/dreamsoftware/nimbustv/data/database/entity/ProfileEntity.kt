package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey val uuid: String,
    val alias: String,
    val isSecured: Boolean,
    val avatarType: String
)