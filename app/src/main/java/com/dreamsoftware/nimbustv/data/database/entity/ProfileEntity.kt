package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@TableName("profiles")
@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey(autoGenerate = true)
    override val id: Long = 0L,
    val alias: String,
    val avatarType: String,
    val securedPin: String
): IEntity