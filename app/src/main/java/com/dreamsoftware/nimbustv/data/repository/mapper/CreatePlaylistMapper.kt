package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.domain.model.CreatePlayListBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import java.util.Date
import java.util.UUID

internal class CreatePlaylistMapper: IOneSideMapper<CreatePlayListBO, PlayListEntity> {

    override fun mapInToOut(input: CreatePlayListBO): PlayListEntity = with(input) {
        PlayListEntity(
            id = UUID.randomUUID().toString(),
            alias = alias,
            createdAt = Date(),
            url = url,
            profileId = profileId
        )
    }

    override fun mapInListToOutList(input: Iterable<CreatePlayListBO>): Iterable<PlayListEntity> =
        input.map(::mapInToOut)
}