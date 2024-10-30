package com.dreamsoftware.nimbustv.ui.screens.epg.model

import com.dreamsoftware.nimbustv.domain.model.ScheduleTypeEnum
import java.time.LocalDateTime

data class ScheduleVO(
    val id: String,
    val channelId: String,
    val channelLogoUrl: String?,
    val channelName: String,
    val type: ScheduleTypeEnum,
    val programmeTitle: String?,
    val programmeDescription: String?,
    val progress: Int?,
    val startTime: LocalDateTime?,
    val endTime: LocalDateTime?
) {
    fun hasTimeData() = startTime != null && endTime != null

    fun isLiveNow() = type == ScheduleTypeEnum.LIVE_NOW

    fun isPast() = type == ScheduleTypeEnum.PAST

    fun isFuture() = type == ScheduleTypeEnum.FUTURE
}
