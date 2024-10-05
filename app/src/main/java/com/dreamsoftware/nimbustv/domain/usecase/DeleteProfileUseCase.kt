package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class DeleteProfileUseCase(
    private val profilesRepository: IProfilesRepository
) : FudgeTvUseCaseWithParams<DeleteProfileUseCase.Params, Boolean>() {

    override suspend fun onExecuted(params: Params): Boolean =
        profilesRepository.deleteProfile(params.profileId)

    data class Params(
        val profileId: Long
    )
}