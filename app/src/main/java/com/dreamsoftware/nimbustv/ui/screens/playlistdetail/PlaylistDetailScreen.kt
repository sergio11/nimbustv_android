package com.dreamsoftware.nimbustv.ui.screens.playlistdetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

data class PlaylistDetailScreenArgs(
    val id: String
)

@Composable
fun PlaylistDetailScreen(
    args: PlaylistDetailScreenArgs,
    viewModel: PlaylistDetailViewModel = hiltViewModel(),
    onBackPressed: () -> Unit,
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { PlaylistDetailUiState() },
        onInit = {
            fetchData(args.id)
        }
    ) { uiState ->
        PlaylistDetailScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}