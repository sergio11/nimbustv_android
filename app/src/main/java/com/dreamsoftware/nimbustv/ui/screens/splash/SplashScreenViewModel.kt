package com.dreamsoftware.nimbustv.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.domain.usecase.GetProfilesCountUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val getProfilesCountUseCase: GetProfilesCountUseCase
): FudgeTvViewModel<SplashUiState, SplashSideEffects>() {

    private companion object {
        const val NO_PROFILES_CREATED = 0L
    }

    override fun onGetDefaultState(): SplashUiState = SplashUiState()

    fun verifySession() {
        viewModelScope.launch {
            delay(4000)
            checkProfiles()
        }
    }

    private fun checkProfiles() {
        executeUseCase(
            useCase = getProfilesCountUseCase,
            onSuccess = ::onCheckProfilesCompleted,
            onFailed = ::onCheckProfilesFailed
        )
    }

    private fun onCheckProfilesCompleted(profilesCount: Long) {
        launchSideEffect(
            if(NO_PROFILES_CREATED == profilesCount) {
                SplashSideEffects.NoProfilesCreated
            } else {
                SplashSideEffects.ProfileSelectionRequired
            }
        )
    }

    private fun onCheckProfilesFailed() {
        launchSideEffect(SplashSideEffects.NoProfilesCreated)
    }
}

data class SplashUiState(
    override var isLoading: Boolean = false,
    override var errorMessage: String? = null,
    val isAuth: Boolean = false
): UiState<SplashUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SplashUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SplashSideEffects: SideEffect {
    data object NoProfilesCreated: SplashSideEffects
    data object ProfileSelectionRequired: SplashSideEffects
}