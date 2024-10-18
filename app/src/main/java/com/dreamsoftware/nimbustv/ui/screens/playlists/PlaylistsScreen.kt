package com.dreamsoftware.nimbustv.ui.screens.playlists

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

@Composable
fun PlaylistsScreen(
    viewModel: PlaylistsViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { PlaylistsUiState() },
        onInit = {
            fetchData()
        }
    ) { uiState ->
        PlaylistsScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}