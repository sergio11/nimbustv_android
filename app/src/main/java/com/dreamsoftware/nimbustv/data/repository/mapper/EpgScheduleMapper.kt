package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity
import com.dreamsoftware.nimbustv.domain.model.EpgScheduleBO
import com.dreamsoftware.nimbustv.domain.model.ScheduleTypeEnum
import com.dreamsoftware.nimbustv.domain.utils.calculateProgress
import com.dreamsoftware.nimbustv.domain.utils.isLiveNow
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import java.time.LocalDateTime

internal class EpgScheduleMapper: IOneSideMapper<ChannelScheduleEntity, EpgScheduleBO> {

    override fun mapInToOut(input: ChannelScheduleEntity): EpgScheduleBO = with(input) {
        val programmeType = getProgrammeType()
        EpgScheduleBO(
            id = id,
            channelId = channelId,
            title = title,
            description = description,
            type = programmeType,
            progress = getProgrammeProgress(programmeType),
            startTime = startTime,
            endTime = endTime
        )
    }

    override fun mapInListToOutList(input: Iterable<ChannelScheduleEntity>): Iterable<EpgScheduleBO> =
        input.map(::mapInToOut)

    private fun ChannelScheduleEntity.getProgrammeType(): ScheduleTypeEnum = when {
        isLiveNow(startTime, endTime) -> ScheduleTypeEnum.LIVE_NOW
        endTime.isBefore(LocalDateTime.now()) -> ScheduleTypeEnum.PAST
        startTime.isAfter(LocalDateTime.now()) -> ScheduleTypeEnum.FUTURE
        else -> ScheduleTypeEnum.UNKNOWN
    }

    private fun ChannelScheduleEntity.getProgrammeProgress(programmeType: ScheduleTypeEnum): Int = when (programmeType) {
        ScheduleTypeEnum.LIVE_NOW -> calculateProgress(startTime, endTime)
        ScheduleTypeEnum.PAST -> 100
        else -> 0
    }
}