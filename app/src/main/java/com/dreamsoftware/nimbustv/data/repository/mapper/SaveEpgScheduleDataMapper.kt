package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity
import com.dreamsoftware.nimbustv.domain.model.CreateEpgScheduleBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class SaveEpgScheduleDataMapper: IOneSideMapper<CreateEpgScheduleBO, ChannelScheduleEntity> {

    override fun mapInToOut(input: CreateEpgScheduleBO): ChannelScheduleEntity = with(input) {
        ChannelScheduleEntity(
            id = id,
            channelId = channelId,
            title = title,
            startTime = startTime,
            endTime = endTime,
            description = description,
            epgId = epgId
        )
    }

    override fun mapInListToOutList(input: Iterable<CreateEpgScheduleBO>): Iterable<ChannelScheduleEntity> =
        input.map(::mapInToOut)
}