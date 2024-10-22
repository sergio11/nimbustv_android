package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgParserService
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService

class SaveEpgUseCase(
    private val profileRepository: IProfilesRepository,
    private val epgParserService: IEpgParserService,
    private val epgRepository: IEpgRepository,
    private val epgSchedulerService: IEpgSchedulerService
) : FudgeTvUseCaseWithParams<SaveEpgUseCase.Params, List<EpgDataBO>>() {

    override suspend fun onExecuted(params: Params): List<EpgDataBO> = with(params) {
        val profileSelected = profileRepository.getProfileSelected()
        val data = epgParserService.parseEpgData(
            profileId = profileSelected.id,
            url = url
        )
        with(epgRepository) {
            deleteAllByProfileId(profileSelected.id)
            epgRepository.save(data)
        }.also {
            epgSchedulerService.scheduleSyncEpgWorkForProfile(
                url = url,
                profileId = profileSelected.id
            )
        }
    }

    data class Params(
        val url: String
    )
}