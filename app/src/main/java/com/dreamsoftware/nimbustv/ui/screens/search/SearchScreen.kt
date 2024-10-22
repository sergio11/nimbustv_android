package com.dreamsoftware.nimbustv.ui.screens.search

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum

@Composable
fun SearchScreen(
    viewModel: SearchViewModel = hiltViewModel(),
    onPlayChannel: (String, StreamTypeEnum) -> Unit,
    onBackPressed: () -> Unit,
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onBackPressed = onBackPressed,
        onInitialUiState = { SearchUiState() },
        onSideEffect = {
            when(it) {
                is SearchSideEffects.PlayChannelSideEffect -> onPlayChannel(it.channelId, it.streamType)
            }
        },
        onInit = {
            fetchData()
        }
    ) { uiState ->
        SearchScreenContent(
            uiState = uiState,
            actionListener = viewModel
        )
    }
}