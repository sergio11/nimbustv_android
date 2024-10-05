package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class HasMultiplesProfilesUseCase(
    private val profilesRepository: IProfilesRepository
): FudgeTvUseCase<Boolean>() {

    private companion object {
        const val ONLY_ONE_PROFILE = 1
    }

    override suspend fun onExecuted(): Boolean =
        profilesRepository.countProfiles() > ONLY_ONE_PROFILE
}