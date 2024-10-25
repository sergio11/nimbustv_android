package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class SaveChannelEpgDataMapper: IOneSideMapper<ChannelEpgDataBO, ChannelEpgEntity> {

    override fun mapInToOut(input: ChannelEpgDataBO): ChannelEpgEntity = with(input) {
        ChannelEpgEntity(
            id = channelId,
            displayName = displayName,
            profileId = profileId
        )
    }

    override fun mapInListToOutList(input: Iterable<ChannelEpgDataBO>): Iterable<ChannelEpgEntity> =
        input.map(::mapInToOut)
}