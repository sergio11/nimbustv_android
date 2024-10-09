package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class ChannelsMapper: IOneSideMapper<ChannelEntity, ChannelBO> {

    override fun mapInToOut(input: ChannelEntity): ChannelBO = with(input) {
        ChannelBO(
            id = id,
            title = title,
            url = url,
            icon = icon,
            category = category,
            manifestType = manifestType,
            licenseKey = licenseKey,
            licenseType = licenseType,
            streamTypeEnum = streamType
        )
    }

    override fun mapInListToOutList(input: Iterable<ChannelEntity>): Iterable<ChannelBO> =
        input.map(::mapInToOut)
}