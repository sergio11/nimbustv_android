package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity
import com.dreamsoftware.nimbustv.domain.model.EpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.EpgScheduleBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class EpgDataMapper(
    private val epgScheduleDataMapper: IOneSideMapper<ChannelScheduleEntity, EpgScheduleBO>
) : IOneSideMapper<EpgDataInput, List<EpgChannelBO>> {

    // Maps the input data to the output data
    override fun mapInToOut(input: EpgDataInput): List<EpgChannelBO> {
        // Create a map to associate channels with their respective programmes
        val channelMap = mutableMapOf<String, Pair<String, MutableList<EpgScheduleBO>>>()

        // Fill the map with channels
        input.channelEpgList.forEach { channelEntity ->
            // Initialize an empty programme list for this channel
            channelMap[channelEntity.id] = Pair(channelEntity.displayName, mutableListOf())
        }

        // Fill the map with programmes associated with each channel
        input.programmeList.forEach { programmeEntity ->
            channelMap[programmeEntity.channelId]?.second?.add(epgScheduleDataMapper.mapInToOut(programmeEntity))
        }

        // Transform the map into a list of EpgDataBO
        return channelMap.map { (channelId, data) ->
            EpgChannelBO(
                channelId = channelId,
                displayName = data.first,
                schedules = data.second
            )
        }
    }

    // Maps a list of input data to a list of output data
    override fun mapInListToOutList(input: Iterable<EpgDataInput>): Iterable<List<EpgChannelBO>> =
        input.map(::mapInToOut)
}

// Definition of input data classes
data class EpgDataInput(
    val channelEpgList: List<ChannelEpgEntity>, // List of channel entities
    val programmeList: List<ChannelScheduleEntity> // List of programme entities
)
