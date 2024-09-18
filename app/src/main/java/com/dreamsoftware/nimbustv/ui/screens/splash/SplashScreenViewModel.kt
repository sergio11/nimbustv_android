package com.dreamsoftware.nimbustv.ui.screens.splash

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashScreenViewModel @Inject constructor(): FudgeTvViewModel<SplashUiState, SplashSideEffects>() {

    override fun onGetDefaultState(): SplashUiState = SplashUiState()

    fun verifySession() {
        viewModelScope.launch {
            delay(4000)
            onCheckProfilesCompleted(false)
        }
    }

    private fun checkProfiles() {

    }

    private fun onCheckProfilesCompleted(hasMultipleProfiles: Boolean) {
        launchSideEffect(
            if(hasMultipleProfiles) {
                SplashSideEffects.ProfileSelectionRequired
            } else {
                SplashSideEffects.UserProfileSelected
            }
        )
    }

    private fun onCheckProfilesFailed() {
        launchSideEffect(SplashSideEffects.ProfileSelectionRequired)
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
    data object UserProfileSelected: SplashSideEffects
    data object ProfileSelectionRequired: SplashSideEffects
}