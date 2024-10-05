package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class GetProfileByIdUseCase(
    private val profilesRepository: IProfilesRepository
): FudgeTvUseCaseWithParams<GetProfileByIdUseCase.Params, ProfileBO>() {

    override suspend fun onExecuted(params: Params): ProfileBO =
        profilesRepository.getProfileById(params.profileId)

    data class Params(
        val profileId: Long
    )
}