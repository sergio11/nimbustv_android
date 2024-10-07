package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.domain.model.SaveChannelBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import java.util.UUID

internal class SaveChannelsMapper: IOneSideMapper<SaveChannelBO, ChannelEntity> {

    override fun mapInToOut(input: SaveChannelBO): ChannelEntity = with(input) {
        ChannelEntity(
            id = UUID.randomUUID().toString(),
            title = title,
            url = url,
            icon = icon,
            category = category,
            playlistId = playlistId
        )
    }

    override fun mapInListToOutList(input: Iterable<SaveChannelBO>): Iterable<ChannelEntity> =
        input.map(::mapInToOut)
}