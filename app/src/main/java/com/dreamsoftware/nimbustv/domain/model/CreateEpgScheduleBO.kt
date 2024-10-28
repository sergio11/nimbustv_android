package com.dreamsoftware.nimbustv.domain.model

import java.time.LocalDateTime

data class CreateEpgScheduleBO(
    val id: String,
    val channelId: String,
    val title: String,
    val description: String?,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val epgId: String
)
