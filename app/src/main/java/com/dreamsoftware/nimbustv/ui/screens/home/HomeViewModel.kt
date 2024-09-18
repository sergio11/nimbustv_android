package com.dreamsoftware.nimbustv.ui.screens.home

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : FudgeTvViewModel<HomeUiState, HomeSideEffects>(), HomeScreenActionListener {

    override fun onGetDefaultState(): HomeUiState = HomeUiState()

    fun fetchData() {

    }
}

data class HomeUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null
): UiState<HomeUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): HomeUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface HomeSideEffects: SideEffect