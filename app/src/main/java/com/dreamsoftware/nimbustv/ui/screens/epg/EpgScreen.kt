package com.dreamsoftware.nimbustv.ui.screens.epg

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

@Composable
fun EpgScreen(
    viewModel: EpgViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { EpgUiState() },
        onInit = {
            fetchData()
        }
    ) { uiState ->
        EpgScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}