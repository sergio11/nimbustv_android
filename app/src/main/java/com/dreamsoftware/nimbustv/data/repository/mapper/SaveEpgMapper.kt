package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.EpgEntity
import com.dreamsoftware.nimbustv.domain.model.CreateEpgBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class SaveEpgMapper: IOneSideMapper<CreateEpgBO, EpgEntity> {

    override fun mapInToOut(input: CreateEpgBO): EpgEntity = with(input) {
        EpgEntity(
            id = id,
            alias = alias,
            url = originalUrl,
            channelsCount = channelList.size.toLong(),
            profileId = profileId
        )
    }

    override fun mapInListToOutList(input: Iterable<CreateEpgBO>): Iterable<EpgEntity> =
        input.map(::mapInToOut)
}