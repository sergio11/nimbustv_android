package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class HasProfilesCountUseCase(
    private val profilesRepository: IProfilesRepository
): FudgeTvUseCase<Boolean>() {

    override suspend fun onExecuted(): Boolean =
        profilesRepository.countProfiles() > 0
}