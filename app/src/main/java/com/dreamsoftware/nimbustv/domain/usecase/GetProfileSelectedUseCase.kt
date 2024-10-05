package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class GetProfileSelectedUseCase(
    private val profilesRepository: IProfilesRepository
): FudgeTvUseCase<ProfileBO>() {
    override suspend fun onExecuted(): ProfileBO =
        profilesRepository.getProfileSelected()
}