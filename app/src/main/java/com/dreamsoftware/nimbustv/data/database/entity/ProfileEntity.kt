package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@TableName("profiles")
@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey
    override val id: String,
    val alias: String,
    val avatarType: String,
    val securedPin: String
): IEntity<String>