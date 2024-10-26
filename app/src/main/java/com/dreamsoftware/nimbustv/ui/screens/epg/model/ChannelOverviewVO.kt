package com.dreamsoftware.nimbustv.ui.screens.epg.model

data class ChannelOverviewVO(
    val title: String,
    val logo: String?,
    val schedules: List<ScheduleVO>
)
