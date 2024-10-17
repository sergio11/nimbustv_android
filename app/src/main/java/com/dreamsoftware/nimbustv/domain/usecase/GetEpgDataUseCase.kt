package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class GetEpgDataUseCase(
    private val profileRepository: IProfilesRepository,
    private val epgRepository: IEpgRepository
) : FudgeTvUseCase<List<EpgDataBO>>() {

    override suspend fun onExecuted(): List<EpgDataBO> {
        val profileSelected = profileRepository.getProfileSelected()
        return epgRepository.findAllByProfileId(profileSelected.id)
    }
}