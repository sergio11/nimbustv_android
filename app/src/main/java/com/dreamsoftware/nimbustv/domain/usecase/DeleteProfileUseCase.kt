package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService

class DeleteProfileUseCase(
    private val profilesRepository: IProfilesRepository,
    private val epgSchedulerService: IEpgSchedulerService
) : FudgeTvUseCaseWithParams<DeleteProfileUseCase.Params, Boolean>() {

    override suspend fun onExecuted(params: Params): Boolean = with(params) {
        profilesRepository.deleteProfile(profileId).also {
            epgSchedulerService.cancelSyncEpgWork(profileId)
        }
    }

    data class Params(
        val profileId: String
    )
}