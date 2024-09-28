package com.dreamsoftware.nimbustv.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.hilt.navigation.compose.hiltViewModel
import com.dreamsoftware.fudge.component.FudgeTvScreen

@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    FudgeTvScreen(
        viewModel = viewModel,
        onInitialUiState = { HomeUiState() },
        onSideEffect = {},
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

