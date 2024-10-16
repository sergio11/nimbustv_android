package com.dreamsoftware.nimbustv.domain.model

import java.time.LocalDateTime

data class EpgDataBO(
    val channelId: String,
    val displayName: String,
    val programmeList: List<ProgrammeDataBO>
)

data class ProgrammeDataBO(
    val id: String,
    val channelId: String,
    val title: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
