package com.dreamsoftware.nimbustv.domain.model

data class EpgChannelBO(
    val channelId: String,
    val displayName: String,
    val schedules: List<EpgScheduleBO>
)
