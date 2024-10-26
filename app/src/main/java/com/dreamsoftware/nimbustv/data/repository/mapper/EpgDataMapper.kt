package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.ProgrammeEntity
import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeDataBO
import com.dreamsoftware.nimbustv.domain.model.ProgrammeType
import com.dreamsoftware.nimbustv.domain.utils.calculateProgress
import com.dreamsoftware.nimbustv.domain.utils.isLiveNow
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import java.time.LocalDateTime

internal class EpgDataMapper : IOneSideMapper<EpgDataInput, List<ChannelEpgDataBO>> {

    // Maps the input data to the output data
    override fun mapInToOut(input: EpgDataInput): List<ChannelEpgDataBO> {
        // Create a map to associate channels with their respective programmes
        val channelMap = mutableMapOf<String, Triple<String, String, MutableList<ProgrammeDataBO>>>()

        // Fill the map with channels
        input.channelEpgList.forEach { channelEntity ->
            // Initialize an empty programme list for this channel
            channelMap[channelEntity.id] = Triple(channelEntity.displayName, channelEntity.profileId, mutableListOf())
        }

        // Fill the map with programmes associated with each channel
        input.programmeList.forEach { programmeEntity ->
            with(programmeEntity) {
                val programmeType = when {
                    isLiveNow(startTime, endTime) -> ProgrammeType.LIVE_NOW
                    endTime.isBefore(LocalDateTime.now()) -> ProgrammeType.PAST
                    startTime.isAfter(LocalDateTime.now()) -> ProgrammeType.FUTURE
                    else -> ProgrammeType.UNKNOWN
                }
                val progress = when (programmeType) {
                    ProgrammeType.LIVE_NOW -> calculateProgress(startTime, endTime)
                    ProgrammeType.PAST -> 100
                    else -> 0
                }
                // Create a ProgrammeDataBO object from ProgrammeEntity
                val programme = ProgrammeDataBO(
                    id = id,
                    channelId = channelId,
                    title = title,
                    startTime = startTime,
                    endTime = endTime,
                    type = programmeType,
                    progress = progress,
                    description = description
                )
                // Add the programme to the corresponding channel's list
                channelMap[channelId]?.third?.add(programme)
            }
        }

        // Transform the map into a list of EpgDataBO
        return channelMap.map { (channelId, data) ->
            ChannelEpgDataBO(
                channelId = channelId,
                displayName = data.first,
                profileId = data.second,
                programmeList = data.third
            )
        }
    }

    // Maps a list of input data to a list of output data
    override fun mapInListToOutList(input: Iterable<EpgDataInput>): Iterable<List<ChannelEpgDataBO>> =
        input.map(::mapInToOut)
}

// Definition of input data classes
data class EpgDataInput(
    val channelEpgList: List<ChannelEpgEntity>, // List of channel entities
    val programmeList: List<ProgrammeEntity> // List of programme entities
)
