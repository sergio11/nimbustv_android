package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO
import com.dreamsoftware.nimbustv.domain.repository.IPreferencesRepository

class GetUserPreferencesUseCase(
    private val preferencesRepository: IPreferencesRepository,
) : FudgeTvUseCase<UserPreferenceBO>() {

    override suspend fun onExecuted(): UserPreferenceBO =
        preferencesRepository.getUserPreferences()
}