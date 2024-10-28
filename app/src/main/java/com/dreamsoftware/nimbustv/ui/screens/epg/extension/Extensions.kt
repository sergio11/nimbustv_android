package com.dreamsoftware.nimbustv.ui.screens.epg.extension

import com.dreamsoftware.nimbustv.domain.model.EpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.EpgScheduleBO
import com.dreamsoftware.nimbustv.domain.model.ScheduleTypeEnum
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ChannelOverviewVO
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ScheduleVO
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import com.dreamsoftware.nimbustv.utils.combinedLet
import java.time.format.DateTimeFormatter

internal fun List<EpgChannelBO>.mapToLiveScheduleList(): List<ScheduleVO> =
    map {
        val liveSchedule = it.schedules.find { programme -> programme.type == ScheduleTypeEnum.LIVE_NOW}
        ScheduleVO(
            channelId = it.channelId,
            channelLogoUrl = null,
            channelName = it.displayName,
            type = ScheduleTypeEnum.LIVE_NOW,
            programmeTitle = liveSchedule?.title,
            progress = liveSchedule?.progress,
            startTime = liveSchedule?.startTime,
            endTime = liveSchedule?.endTime,
            programmeDescription = liveSchedule?.description
        )
    }


internal fun List<EpgChannelBO>.mapToChannelOverviewList(): List<ChannelOverviewVO> =
    map {
        ChannelOverviewVO(
            title = it.displayName,
            logo = null,
            schedules = it.schedules.mapToScheduleList(it.displayName)
        )
    }


internal fun List<EpgScheduleBO>.mapToScheduleList(channelName: String): List<ScheduleVO> =
    map {
        ScheduleVO(
            channelId = it.channelId,
            channelLogoUrl = null,
            channelName = channelName,
            type = it.type,
            programmeTitle = it.title,
            progress = it.progress,
            startTime = it.startTime,
            endTime = it.endTime,
            programmeDescription = it.description
        )
    }

internal fun ScheduleVO.toScheduleFormatted(): String =
    combinedLet(startTime, endTime) { startTime, endTime ->
        "${startTime.format(DateTimeFormatter.ofPattern("hh:mm a"))} - " +
                endTime.format(DateTimeFormatter.ofPattern("hh:mm a"))
    } ?: String.EMPTY

internal fun List<EpgChannelBO>.filterSchedulesByChannel(channelId: String): List<ScheduleVO> =
    filter { channel -> channel.channelId == channelId }
    .flatMap { channel -> channel.schedules.mapToScheduleList(channel.displayName) }
    .sortedBy(ScheduleVO::startTime)
