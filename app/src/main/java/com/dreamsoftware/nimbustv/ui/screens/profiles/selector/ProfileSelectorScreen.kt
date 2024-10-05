package com.dreamsoftware.nimbustv.ui.screens.profiles.selector

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

@Composable
fun ProfileSelectorScreen(
    viewModel: ProfileSelectorViewModel = hiltViewModel(),
    onProfileLocked: (String) -> Unit,
    onGoToAddProfile: () -> Unit,
    onGoToProfileManagement: () -> Unit,
    onCancelProfileSelection: () -> Unit
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onInit = {  load() },
        onInitialUiState = { ProfileSelectorUiState() },
        onSideEffect = {
            when(it) {
                is ProfileSelectorSideEffects.ProfileLocked -> onProfileLocked(it.profileId)
                ProfileSelectorSideEffects.AddNewProfile -> onGoToAddProfile()
                ProfileSelectorSideEffects.ConfigureProfiles -> onGoToProfileManagement()
                ProfileSelectorSideEffects.CancelProfileSelection -> onCancelProfileSelection()
            }
        }
    ) { uiState ->
        ProfileSelectorContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}