package com.dreamsoftware.nimbustv.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@TableName("profiles")
@Entity(tableName = "profiles")
data class ProfileEntity(
    @PrimaryKey
    override val id: String,
    val alias: String,
    @ColumnInfo(name = "avatar_type")
    val avatarType: String,
    @ColumnInfo(name = "secured_pin")
    val securedPin: String
): IEntity<String>