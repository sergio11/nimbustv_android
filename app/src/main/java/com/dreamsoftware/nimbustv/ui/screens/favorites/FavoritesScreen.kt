package com.dreamsoftware.nimbustv.ui.screens.favorites

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum

@Composable
fun FavoritesScreen(
    viewModel: FavoritesViewModel = hiltViewModel(),
    onPlayChannel: (String, StreamTypeEnum) -> Unit,
    onBackPressed: () -> Unit,
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { FavoritesUiState() },
        onSideEffect = {
            when(it) {
                is FavoritesSideEffects.PlayChannelSideEffect -> onPlayChannel(it.channelId, it.streamType)
            }
        },
        onInit = {
            fetchData()
        }
    ) { uiState ->
        FavoritesScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}