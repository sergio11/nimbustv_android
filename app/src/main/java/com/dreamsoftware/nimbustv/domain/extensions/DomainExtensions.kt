package com.dreamsoftware.nimbustv.domain.extensions

import com.dreamsoftware.nimbustv.domain.model.CreateEpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.CreateEpgScheduleBO
import com.dreamsoftware.nimbustv.domain.model.EpgChannelEntryBO
import java.util.UUID

private const val SECURE_PIN_LENGTH = 6
private const val MIN_ALIAS_LENGTH = 5

fun Int.isSecurePinValid() = toString().length == SECURE_PIN_LENGTH

fun Int.isSecurePinNotValid() = !isSecurePinValid()

fun String.isProfileAliasValid() = length >= MIN_ALIAS_LENGTH

fun String.isProfileAliasNotValid() = !isProfileAliasValid()

fun List<EpgChannelEntryBO>.mapToCreateEpgChannelBO(epgId: String) = map { channel ->
    CreateEpgChannelBO(
        channelId = channel.channelId,
        displayName = channel.displayName,
        epgId = epgId,
        programmeList = channel.programmeList.map { schedule ->
            CreateEpgScheduleBO(
                id = UUID.randomUUID().toString(),
                channelId = channel.channelId,
                title = schedule.title,
                description = schedule.description,
                startTime = schedule.startTime,
                endTime = schedule.endTime,
                epgId = epgId
            )
        }
    )
}