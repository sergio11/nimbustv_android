package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class GetProfilesUseCase(
    private val profilesRepository: IProfilesRepository
): FudgeTvUseCase<List<ProfileBO>>() {

    override suspend fun onExecuted(): List<ProfileBO> =
        profilesRepository.getProfiles()
}