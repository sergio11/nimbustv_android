package com.dreamsoftware.nimbustv.ui.screens.splash

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

@Composable
fun SplashScreen(
    modifier: Modifier = Modifier,
    viewModel: SplashScreenViewModel = hiltViewModel(),
    onNoProfilesCreated: () -> Unit,
    onProfileSelectionRequired: () -> Unit
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onInitialUiState = { SplashUiState() },
        onSideEffect = {
            when(it) {
                SplashSideEffects.ConfirmProfileRequired -> onProfileSelectionRequired()
                SplashSideEffects.NoProfilesAvailable -> onNoProfilesCreated()
            }
        },
        onInit = {
            verifySession()
        }
    ) { uiState ->
        SplashScreenContent(
            modifier = modifier,
            uiState = uiState
        )
    }
}