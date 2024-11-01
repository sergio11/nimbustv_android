package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.domain.model.CreateEpgChannelBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class SaveEpgChannelDataMapper: IOneSideMapper<CreateEpgChannelBO, ChannelEpgEntity> {

    override fun mapInToOut(input: CreateEpgChannelBO): ChannelEpgEntity = with(input) {
        ChannelEpgEntity(
            id = id,
            originalChannelId = channelId,
            displayName = displayName,
            epgId = epgId
        )
    }

    override fun mapInListToOutList(input: Iterable<CreateEpgChannelBO>): Iterable<ChannelEpgEntity> =
        input.map(::mapInToOut)
}