package com.dreamsoftware.nimbustv.ui.screens.epg.extension

import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeType
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ScheduleVO
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import java.time.format.DateTimeFormatter

internal fun List<ChannelEpgDataBO>.mapToLiveScheduleList(): List<ScheduleVO> =
    map {
        val liveSchedule = it.programmeList.find { programme -> programme.type == ProgrammeType.LIVE_NOW}
        ScheduleVO(
            channelId = it.channelId,
            channelName = it.displayName,
            type = ProgrammeType.LIVE_NOW,
            programmeTitle = liveSchedule?.title,
            progress = liveSchedule?.progress,
            startTime = liveSchedule?.startTime,
            endTime = liveSchedule?.endTime
        )
    }


internal fun List<ProgrammeDataBO>.mapToScheduleList(channelName: String): List<ScheduleVO> =
    map {
        ScheduleVO(
            channelId = it.channelId,
            channelName = channelName,
            type = it.type,
            programmeTitle = it.title,
            progress = it.progress,
            startTime = it.startTime,
            endTime = it.endTime
        )
    }

internal fun ScheduleVO.toScheduleFormatted() =
    if(startTime != null && endTime != null) {
        "${startTime.format(DateTimeFormatter.ofPattern("HH:mm"))} - " +
                endTime.format(DateTimeFormatter.ofPattern("HH:mm"))
    } else {
        String.EMPTY
    }
