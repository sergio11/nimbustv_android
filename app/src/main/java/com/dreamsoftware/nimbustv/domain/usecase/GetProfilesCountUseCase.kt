package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class GetProfilesCountUseCase(
    private val profilesRepository: IProfilesRepository
): FudgeTvUseCase<Long>() {

    override suspend fun onExecuted(): Long =
        profilesRepository.countProfiles()
}