package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class SignOffUseCase(
    private val profilesRepository: IProfilesRepository,
    private val cancelProfileRemindersUseCase: CancelProfileRemindersUseCase,
): FudgeTvUseCase<Unit>() {
    override suspend fun onExecuted() {
        with(profilesRepository) {
            cancelProfileRemindersUseCase.onExecuted(
                CancelProfileRemindersUseCase.Params(
                    profileId = getProfileSelected().id
                )
            )
            clearProfileSelected()
        }
    }
}