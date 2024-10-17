package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class DeleteEpgDataUseCase(
    private val profileRepository: IProfilesRepository,
    private val epgRepository: IEpgRepository
) : FudgeTvUseCase<Unit>() {

    override suspend fun onExecuted() {
        val profileSelected = profileRepository.getProfileSelected()
        epgRepository.deleteAllByProfileId(profileSelected.id)
    }
}