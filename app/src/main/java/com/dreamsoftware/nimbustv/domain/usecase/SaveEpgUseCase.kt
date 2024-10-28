package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.extensions.mapToCreateEpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.CreateEpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgBO
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
        epgRepository.create(
            CreateEpgBO(
                id = epgId,
                alias = alias,
                profileId = profileSelected.id,
                originalUrl = url,
                channelList = data.mapToCreateEpgChannelBO(epgId)
            )
        ).also {
            epgSchedulerService.scheduleSyncEpgWork(epgId = epgId)
        }
    }

    data class Params(
        val alias: String,
        val url: String
    )
}