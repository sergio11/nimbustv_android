package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.ProgrammeEntity
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeDataBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class EpgDataMapper : IOneSideMapper<EpgDataInput, List<EpgDataBO>> {

    // Maps the input data to the output data
    override fun mapInToOut(input: EpgDataInput): List<EpgDataBO> {
        // Create a map to associate channels with their respective programmes
        val channelMap = mutableMapOf<String, Pair<String, MutableList<ProgrammeDataBO>>>()

        // Fill the map with channels
        input.channelEpgList.forEach { channelEntity ->
            val channelId = channelEntity.id
            val displayName = channelEntity.displayName
            // Initialize an empty programme list for this channel
            channelMap[channelId] = Pair(displayName, mutableListOf())
        }

        // Fill the map with programmes associated with each channel
        input.programmeList.forEach { programmeEntity ->
            val channelId = programmeEntity.channelId
            // Create a ProgrammeDataBO object from ProgrammeEntity
            val programme = ProgrammeDataBO(
                id = programmeEntity.id,
                channelId = channelId,
                title = programmeEntity.title,
                startTime = programmeEntity.startTime,
                endTime = programmeEntity.endTime
            )
            // Add the programme to the corresponding channel's list
            channelMap[channelId]?.second?.add(programme)
        }

        // Transform the map into a list of EpgDataBO
        return channelMap.map { (channelId, pair) ->
            EpgDataBO(channelId, pair.first, pair.second)
        }
    }

    // Maps a list of input data to a list of output data
    override fun mapInListToOutList(input: Iterable<EpgDataInput>): Iterable<List<EpgDataBO>> =
        input.map(::mapInToOut)
}

// Definition of input data classes
data class EpgDataInput(
    val channelEpgList: List<ChannelEpgEntity>, // List of channel entities
    val programmeList: List<ProgrammeEntity> // List of programme entities
)
