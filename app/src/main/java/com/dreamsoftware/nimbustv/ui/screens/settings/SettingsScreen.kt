package com.dreamsoftware.nimbustv.ui.screens.settings

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

@Composable
fun SettingsScreen(
    viewModel: SettingsViewModel = hiltViewModel(),
    onGoToSubscriptions: () -> Unit,
    onOpenSystemSettings: () -> Unit,
    onBackPressed: () -> Unit,
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onSideEffect = {
            when(it) {
                SettingsSideEffects.OpenSubscriptions -> onGoToSubscriptions()
                SettingsSideEffects.OpenSystemSettings -> onOpenSystemSettings()
            }
        },
        onInitialUiState = { SettingsUiState() },
        onInit = {
            fetchData()
        }
    ) { uiState ->
        SettingsScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}

