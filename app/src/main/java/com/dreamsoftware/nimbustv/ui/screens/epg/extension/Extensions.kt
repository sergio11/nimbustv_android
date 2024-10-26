package com.dreamsoftware.nimbustv.ui.screens.epg.extension

import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeType
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ChannelOverviewVO
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ScheduleVO
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import com.dreamsoftware.nimbustv.utils.combinedLet
import java.time.format.DateTimeFormatter

internal fun List<ChannelEpgDataBO>.mapToLiveScheduleList(): List<ScheduleVO> =
    map {
        val liveSchedule = it.programmeList.find { programme -> programme.type == ProgrammeType.LIVE_NOW}
        ScheduleVO(
            channelId = it.channelId,
            channelLogoUrl = null,
            channelName = it.displayName,
            type = ProgrammeType.LIVE_NOW,
            programmeTitle = liveSchedule?.title,
            progress = liveSchedule?.progress,
            startTime = liveSchedule?.startTime,
            endTime = liveSchedule?.endTime,
            programmeDescription = liveSchedule?.description
        )
    }


internal fun List<ChannelEpgDataBO>.mapToChannelOverviewList(): List<ChannelOverviewVO> =
    map {
        ChannelOverviewVO(
            title = it.displayName,
            logo = null,
            schedules = it.programmeList.mapToScheduleList(it.displayName)
        )
    }


internal fun List<ProgrammeDataBO>.mapToScheduleList(channelName: String): List<ScheduleVO> =
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

internal fun List<ChannelEpgDataBO>.filterSchedulesByChannel(channelId: String): List<ScheduleVO> =
    filter { channel -> channel.channelId == channelId }
    .flatMap { channel -> channel.programmeList.mapToScheduleList(channel.displayName) }
    .sortedBy(ScheduleVO::startTime)
