package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService

class DeleteProfileUseCase(
    private val epgRepository: IEpgRepository,
    private val profilesRepository: IProfilesRepository,
    private val epgSchedulerService: IEpgSchedulerService
) : FudgeTvUseCaseWithParams<DeleteProfileUseCase.Params, Boolean>() {

    override suspend fun onExecuted(params: Params): Boolean = with(params) {
        epgRepository.findAllByProfileId(profileId)
            .forEach { epgSchedulerService.cancelSyncEpgWork(it.id) }
        profilesRepository.deleteProfile(profileId)
    }

    data class Params(
        val profileId: String
    )
}