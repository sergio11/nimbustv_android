package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO
import com.dreamsoftware.nimbustv.domain.repository.IPreferencesRepository

class SaveUserPreferencesUseCase(
    private val preferencesRepository: IPreferencesRepository
) : FudgeTvUseCaseWithParams<SaveUserPreferencesUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params): Unit = with(params) {
        preferencesRepository.saveUserPreferences(toUserPreferencesBO())
    }

    private fun Params.toUserPreferencesBO() = UserPreferenceBO(
        enableSearch = enableSearch,
    )

    data class Params(
        val enableSearch: Boolean
    )
}