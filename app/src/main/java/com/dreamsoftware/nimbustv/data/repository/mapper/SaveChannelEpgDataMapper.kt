package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class SaveChannelEpgDataMapper: IOneSideMapper<EpgDataBO, ChannelEpgEntity> {

    override fun mapInToOut(input: EpgDataBO): ChannelEpgEntity = with(input) {
        ChannelEpgEntity(
            id = channelId,
            displayName = displayName,
            profileId = profileId
        )
    }

    override fun mapInListToOutList(input: Iterable<EpgDataBO>): Iterable<ChannelEpgEntity> =
        input.map(::mapInToOut)
}