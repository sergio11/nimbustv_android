package com.dreamsoftware.nimbustv.ui.screens.playlistdetail

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum

data class PlaylistDetailScreenArgs(
    val id: String
)

@Composable
fun PlaylistDetailScreen(
    args: PlaylistDetailScreenArgs,
    viewModel: PlaylistDetailViewModel = hiltViewModel(),
    onPlayChannel: (String, StreamTypeEnum) -> Unit,
    onBackPressed: () -> Unit,
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { PlaylistDetailUiState() },
        onSideEffect = {
            when(it) {
                PlaylistDetailSideEffects.PlaylistRemovedSideEffect -> onBackPressed()
                is PlaylistDetailSideEffects.PlayChannelSideEffect -> onPlayChannel(it.channelId, it.streamTypeEnum)
            }
        },
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