package com.dreamsoftware.nimbustv.data.database.adapter

import androidx.room.TypeConverter
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.utils.enumNameOfOrDefault
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Date

class Adapters {

    private val formatter: DateTimeFormatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME
    
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? = value?.let { Date(it) }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? = date?.time

    @TypeConverter
    fun fromStreamTypeName(value: String?): StreamTypeEnum? = value?.let { enumNameOfOrDefault(it, StreamTypeEnum.VIDEO) }

    @TypeConverter
    fun streamTypeToName(streamType: StreamTypeEnum?): String? = streamType?.name

    @TypeConverter
    fun fromLocalDateTime(value: LocalDateTime?): String? = value?.format(formatter)

    @TypeConverter
    fun toLocalDateTime(value: String?): LocalDateTime? = value?.let { LocalDateTime.parse(it, formatter) }
}
