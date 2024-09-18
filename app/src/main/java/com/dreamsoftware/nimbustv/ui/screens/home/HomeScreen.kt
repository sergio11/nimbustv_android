package com.dreamsoftware.nimbustv.ui.screens.home

import androidx.compose.runtime.Composable
import androidx.compose.runtime.saveable.Saver
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.tv.material3.CarouselState
import androidx.tv.material3.ExperimentalTvMaterial3Api
import com.dreamsoftware.fudge.component.FudgeTvScreen

@OptIn(ExperimentalTvMaterial3Api::class)
val carouselSaver =
    Saver<CarouselState, Int>(save = { it.activeItemIndex }, restore = { CarouselState(it) })

@OptIn(ExperimentalTvMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: HomeViewModel = hiltViewModel()
) {
    val carouselState = rememberSaveable(saver = carouselSaver) { CarouselState(0) }
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
            carouselState = carouselState,
            actionListener = viewModel
        )
    }
}

