package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.EpgEntity
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class EpgMapper: IOneSideMapper<EpgEntity, EpgBO> {

    override fun mapInToOut(input: EpgEntity): EpgBO = with(input) {
        EpgBO(
            id = id,
            alias = alias,
            creationDate = createdAt,
            lastUpdatedDate = lastUpdatedAt,
            channelsCount = channelsCount
        )
    }

    override fun mapInListToOutList(input: Iterable<EpgEntity>): Iterable<EpgBO> =
        input.map(::mapInToOut)
}