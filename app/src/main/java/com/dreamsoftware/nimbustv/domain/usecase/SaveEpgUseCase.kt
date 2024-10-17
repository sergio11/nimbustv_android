package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgParserService

class SaveEpgUseCase(
    private val profileRepository: IProfilesRepository,
    private val epgParserService: IEpgParserService,
    private val epgRepository: IEpgRepository
) : FudgeTvUseCaseWithParams<SaveEpgUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        val profileSelected = profileRepository.getProfileSelected()
        val data = epgParserService.parseEpgData(
            profileId = profileSelected.id,
            url = params.url
        )
        with(epgRepository) {
            deleteAllByProfileId(profileSelected.id)
            epgRepository.save(data)
        }
    }

    data class Params(
        val url: String
    )
}