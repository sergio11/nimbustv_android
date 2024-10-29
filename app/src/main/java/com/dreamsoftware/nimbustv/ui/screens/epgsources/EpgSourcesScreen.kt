package com.dreamsoftware.nimbustv.ui.screens.epgsources

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

@Composable
fun EpgSourcesScreen(
    viewModel: EpgSourcesViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { EpgSourcesUiState() },
        onInit = {
            fetchData()
        }
    ) { uiState ->
        EpgSourcesScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}