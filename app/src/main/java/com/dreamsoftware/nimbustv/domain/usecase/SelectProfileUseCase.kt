package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class SelectProfileUseCase(
    private val profilesRepository: IProfilesRepository
): FudgeTvUseCaseWithParams<SelectProfileUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params): Unit =
        profilesRepository.selectProfile(params.profile)

    data class Params(
        val profile: ProfileBO
    )
}