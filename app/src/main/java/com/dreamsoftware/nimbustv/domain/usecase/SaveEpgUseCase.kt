package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.CreateEpgBO
import com.dreamsoftware.nimbustv.domain.model.CreateEpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.CreateEpgScheduleBO
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgChannelEntryBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgParserService
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService
import java.util.UUID

class SaveEpgUseCase(
    private val profileRepository: IProfilesRepository,
    private val epgParserService: IEpgParserService,
    private val epgRepository: IEpgRepository,
    private val epgSchedulerService: IEpgSchedulerService
) : FudgeTvUseCaseWithParams<SaveEpgUseCase.Params, EpgBO>() {

    override suspend fun onExecuted(params: Params): EpgBO = with(params) {
        val profileSelected = profileRepository.getProfileSelected()
        val data = epgParserService.parseEpgData(url = url)
        val epgId = UUID.randomUUID().toString()
        epgRepository.save(
            CreateEpgBO(
                id = epgId,
                alias = alias,
                profileId = profileSelected.id,
                originalUrl = url,
                channelList = data.mapToCreateEpgChannelBO(epgId)
            )
        ).also {
            epgSchedulerService.scheduleSyncEpgWorkForProfile(
                url = url,
                profileId = profileSelected.id
            )
        }
    }

    private fun List<EpgChannelEntryBO>.mapToCreateEpgChannelBO(epgId: String) = map { channel ->
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

    data class Params(
        val alias: String,
        val url: String
    )
}