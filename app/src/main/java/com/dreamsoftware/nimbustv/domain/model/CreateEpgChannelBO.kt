package com.dreamsoftware.nimbustv.domain.model

data class CreateEpgChannelBO(
    val channelId: String,
    val displayName: String,
    val epgId: String,
    val programmeList: List<CreateEpgScheduleBO>
)
