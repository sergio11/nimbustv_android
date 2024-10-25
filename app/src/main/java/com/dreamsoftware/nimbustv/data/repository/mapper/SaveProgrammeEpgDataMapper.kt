package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ProgrammeEntity
import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class SaveProgrammeEpgDataMapper: IOneSideMapper<ChannelEpgDataBO, Iterable<ProgrammeEntity>> {

    override fun mapInToOut(input: ChannelEpgDataBO): List<ProgrammeEntity> =
        input.programmeList.map {
            ProgrammeEntity(
                id = it.id,
                channelId = it.channelId,
                title = it.title,
                startTime = it.startTime,
                endTime = it.endTime
            )
        }

    override fun mapInListToOutList(input: Iterable<ChannelEpgDataBO>): Iterable<Iterable<ProgrammeEntity>> =
        input.map(::mapInToOut)
}