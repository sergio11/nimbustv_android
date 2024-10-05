package com.dreamsoftware.nimbustv.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.domain.usecase.GetProfilesCountUseCase
import com.dreamsoftware.nimbustv.domain.usecase.HasProfileSelectedUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(
    private val hasProfileSelectedUseCase: HasProfileSelectedUseCase,
    private val getProfilesCountUseCase: GetProfilesCountUseCase
): FudgeTvViewModel<SplashUiState, SplashSideEffects>() {

    private companion object {
        const val NO_PROFILES_CREATED = 0L
    }

    override fun onGetDefaultState(): SplashUiState = SplashUiState()

    fun verifySession() {
        viewModelScope.launch {
            delay(4000)
            checkCurrentProfileSelected()
        }
    }

    private fun checkCurrentProfileSelected() {
        executeUseCase(
            useCase = hasProfileSelectedUseCase,
            onSuccess = ::onCheckCurrentProfileSelected,
            onFailed = ::onCheckProfilesFailed
        )
    }

    private fun checkProfiles() {
        executeUseCase(
            useCase = getProfilesCountUseCase,
            onSuccess = ::onCheckProfilesCompleted,
            onFailed = ::onCheckProfilesFailed
        )
    }

    private fun onCheckCurrentProfileSelected(hasProfileSelected: Boolean) {
        if(hasProfileSelected) {
            launchSideEffect(SplashSideEffects.ConfirmProfileRequired)
        } else {
            checkProfiles()
        }
    }

    private fun onCheckProfilesCompleted(profilesCount: Long) {
        launchSideEffect(
            if(NO_PROFILES_CREATED == profilesCount) {
                SplashSideEffects.NoProfilesAvailable
            } else {
                SplashSideEffects.ConfirmProfileRequired
            }
        )
    }

    private fun onCheckProfilesFailed() {
        launchSideEffect(SplashSideEffects.NoProfilesAvailable)
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
    data object NoProfilesAvailable: SplashSideEffects
    data object ConfirmProfileRequired: SplashSideEffects
}