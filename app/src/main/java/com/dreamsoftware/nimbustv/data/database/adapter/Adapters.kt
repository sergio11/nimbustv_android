package com.dreamsoftware.nimbustv.data.database.adapter

import androidx.room.TypeConverter
import java.util.Date

class Adapters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time
}
