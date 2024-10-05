package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class SignOffUseCase(
    private val profilesRepository: IProfilesRepository,
): FudgeTvUseCase<Unit>() {
    override suspend fun onExecuted() {
        profilesRepository.clearProfileSelected()
    }
}