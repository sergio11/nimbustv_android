package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity
import com.dreamsoftware.nimbustv.domain.model.EpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.EpgScheduleBO
import com.dreamsoftware.nimbustv.domain.model.ScheduleTypeEnum
import com.dreamsoftware.nimbustv.domain.utils.calculateProgress
import com.dreamsoftware.nimbustv.domain.utils.isLiveNow
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import java.time.LocalDateTime

internal class EpgDataMapper : IOneSideMapper<EpgDataInput, List<EpgChannelBO>> {

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
            with(programmeEntity) {
                val programmeType = when {
                    isLiveNow(startTime, endTime) -> ScheduleTypeEnum.LIVE_NOW
                    endTime.isBefore(LocalDateTime.now()) -> ScheduleTypeEnum.PAST
                    startTime.isAfter(LocalDateTime.now()) -> ScheduleTypeEnum.FUTURE
                    else -> ScheduleTypeEnum.UNKNOWN
                }
                val progress = when (programmeType) {
                    ScheduleTypeEnum.LIVE_NOW -> calculateProgress(startTime, endTime)
                    ScheduleTypeEnum.PAST -> 100
                    else -> 0
                }
                val programme = EpgScheduleBO(
                    id = id,
                    channelId = channelId,
                    title = title,
                    startTime = startTime,
                    endTime = endTime,
                    type = programmeType,
                    progress = progress,
                    description = description
                )
                channelMap[channelId]?.second?.add(programme)
            }
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
