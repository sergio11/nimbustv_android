package com.dreamsoftware.nimbustv.ui.screens.epg.model

import com.dreamsoftware.nimbustv.domain.model.ProgrammeType
import java.time.LocalDateTime

data class ScheduleVO(
    val channelId: String,
    val channelLogoUrl: String?,
    val channelName: String,
    val type: ProgrammeType,
    val programmeTitle: String?,
    val programmeDescription: String?,
    val progress: Int?,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?
) {
    fun hasTimeData() = startTime != null && endTime != null

    fun isLiveNow() = type == ProgrammeType.LIVE_NOW

    fun isPast() = type == ProgrammeType.PAST

    fun isFuture() = type == ProgrammeType.FUTURE
}
