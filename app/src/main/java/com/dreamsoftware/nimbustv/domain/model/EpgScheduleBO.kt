package com.dreamsoftware.nimbustv.domain.model

import java.time.LocalDateTime

data class EpgScheduleBO(
    val id: String,
    val channelId: String,
    val title: String,
    val description: String?,
    val type: ScheduleTypeEnum,
    val progress: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
