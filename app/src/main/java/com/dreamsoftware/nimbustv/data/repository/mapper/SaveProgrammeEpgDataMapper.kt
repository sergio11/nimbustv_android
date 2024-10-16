package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ProgrammeEntity
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class SaveProgrammeEpgDataMapper: IOneSideMapper<EpgDataBO, Iterable<ProgrammeEntity>> {

    override fun mapInToOut(input: EpgDataBO): List<ProgrammeEntity> =
        input.programmeList.map {
            ProgrammeEntity(
                id = it.id,
                channelId = it.channelId,
                title = it.title,
                startTime = it.startTime,
                endTime = it.endTime
            )
        }

    override fun mapInListToOutList(input: Iterable<EpgDataBO>): Iterable<Iterable<ProgrammeEntity>> =
        input.map(::mapInToOut)
}