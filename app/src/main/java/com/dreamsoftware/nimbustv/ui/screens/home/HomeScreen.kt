package com.dreamsoftware.nimbustv.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onPlayChannel: (channelId: String) -> Unit
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onInitialUiState = { HomeUiState() },
        onSideEffect = {
            when (it) {
                is HomeSideEffects.PlayChannelSideEffect -> onPlayChannel(it.id)
            }
        },
        onInit = {
            fetchData()
        }
    ) { uiState ->
        HomeScreenContent(
            state = uiState,
            actionListener = viewModel
        )
    }
}

