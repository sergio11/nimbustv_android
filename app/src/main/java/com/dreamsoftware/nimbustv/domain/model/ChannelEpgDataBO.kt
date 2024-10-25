package com.dreamsoftware.nimbustv.domain.model

import java.time.LocalDateTime

data class ChannelEpgDataBO(
    val channelId: String,
    val displayName: String,
    val profileId: String,
    val programmeList: List<ProgrammeDataBO>
)

enum class ProgrammeType {
    LIVE_NOW,
    PAST,
    FUTURE,
    UNKNOWN
}

data class ProgrammeDataBO(
    val id: String,
    val channelId: String,
    val title: String,
    val type: ProgrammeType,
    val progress: Int,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime
)
