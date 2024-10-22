package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService

class DeleteEpgDataUseCase(
    private val profileRepository: IProfilesRepository,
    private val epgRepository: IEpgRepository,
    private val epgSchedulerService: IEpgSchedulerService
) : FudgeTvUseCase<Unit>() {

    override suspend fun onExecuted() {
        val profileSelected = profileRepository.getProfileSelected()
        epgRepository.deleteAllByProfileId(profileSelected.id)
        epgSchedulerService.cancelSyncEpgWork(profileSelected.id)
    }
}