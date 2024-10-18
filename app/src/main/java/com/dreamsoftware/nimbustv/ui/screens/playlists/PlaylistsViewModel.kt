package com.dreamsoftware.nimbustv.ui.screens.playlists

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.PlaylistsScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.domain.usecase.GetPlaylistsByProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val getPlaylistsByProfileUseCase: GetPlaylistsByProfileUseCase,
    @PlaylistsScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<PlaylistsUiState, PlaylistsSideEffects>(), PlaylistsScreenActionListener {

    override fun onGetDefaultState(): PlaylistsUiState = PlaylistsUiState()

    fun fetchData() {
        executeUseCase(
            useCase = getPlaylistsByProfileUseCase,
            onSuccess = ::onFetchPlaylistsSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onFetchPlaylistsSuccessfully(data: List<PlayListBO>) {
        updateState { it.copy(playlists = data) }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: PlaylistsUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class PlaylistsUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val playlists: List<PlayListBO> = emptyList(),
): UiState<PlaylistsUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): PlaylistsUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface PlaylistsSideEffects: SideEffect