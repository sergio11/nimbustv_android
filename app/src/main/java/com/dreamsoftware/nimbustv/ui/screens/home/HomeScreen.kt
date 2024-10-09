package com.dreamsoftware.nimbustv.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel(),
    onPlayChannel: (channelId: String, streamType: StreamTypeEnum) -> Unit
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onInitialUiState = { HomeUiState() },
        onSideEffect = {
            when (it) {
                is HomeSideEffects.PlayChannelSideEffect -> onPlayChannel(it.id, it.type)
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

