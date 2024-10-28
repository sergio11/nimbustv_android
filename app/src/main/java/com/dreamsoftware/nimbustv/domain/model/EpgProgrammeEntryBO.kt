package com.dreamsoftware.nimbustv.domain.model

import java.time.LocalDateTime

data class EpgProgrammeEntryBO(
    val channelId: String,
    val title: String,
    val description: String?,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
