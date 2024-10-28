package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class GetEpgListUseCase(
    private val profileRepository: IProfilesRepository,
    private val epgRepository: IEpgRepository
) : FudgeTvUseCase<List<EpgBO>>() {

    override suspend fun onExecuted(): List<EpgBO> {
        val profileId = profileRepository.getProfileSelected()
        return epgRepository.findAllByProfileId(profileId.id)
    }
}