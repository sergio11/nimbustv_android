package com.dreamsoftware.nimbustv.domain.model

data class EpgChannelEntryBO(
    val channelId: String,
    val displayName: String,
    val programmeList: List<EpgProgrammeEntryBO>
)
