package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class PlaylistMapper: IOneSideMapper<PlayListEntity, PlayListBO> {

    override fun mapInToOut(input: PlayListEntity): PlayListBO = with(input) {
        PlayListBO(
            id = id,
            alias = alias,
            creationDate = createdAt,
            originalUrl = url,
            profileId = profileId,
            channelsCount = channelsCount
        )
    }

    override fun mapInListToOutList(input: Iterable<PlayListEntity>): Iterable<PlayListBO> =
        input.map(::mapInToOut)
}