package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class VerifyPinUseCase(
    private val profilesRepository: IProfilesRepository
): FudgeTvUseCaseWithParams<VerifyPinUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) = with(params) {
        profilesRepository.verifyPin(profileId, pin)
    }

    data class Params(
        val profileId: Long,
        val pin: Int
    )
}