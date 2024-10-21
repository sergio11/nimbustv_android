package com.dreamsoftware.nimbustv.ui.player.video

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

data class VideoPlayerScreenArgs(
    val id: String
)

@Composable
fun VideoPlayerScreen(
    viewModel: VideoPlayerViewModel = hiltViewModel(),
    args: VideoPlayerScreenArgs,
    onOpenSettings: () -> Unit,
    onBackPressed: () -> Unit
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { VideoPlayerUiState() },
        onSideEffect = {
            when(it) {
                VideoPlayerSideEffects.OpenSettingsSideEffects -> onOpenSettings()
            }
        },
        onInit = {
            with(args) {
                fetchData(id = id)
            }
        }
    ) { uiState ->
        VideoPlayerScreenContent(
            state = uiState,
            actionListener = viewModel
        )
    }
}