package com.dreamsoftware.nimbustv.ui.screens.playlistdetail

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.PlaylistDetailScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelsByPlaylistUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    private val getChannelsByPlaylistUseCase: GetChannelsByPlaylistUseCase,
    @PlaylistDetailScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<PlaylistDetailUiState, PlaylistDetailSideEffects>(), PlaylistDetailScreenActionListener {

    override fun onGetDefaultState(): PlaylistDetailUiState = PlaylistDetailUiState()

    fun fetchData(id: String) {
        executeUseCaseWithParams(
            useCase = getChannelsByPlaylistUseCase,
            params = GetChannelsByPlaylistUseCase.Params(
                playlistId = id
            ),
            onSuccess = ::onGetChannelsByPlaylistCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onMapExceptionToState(ex: Exception, uiState: PlaylistDetailUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    private fun onGetChannelsByPlaylistCompleted(data: List<ChannelBO>) {
        updateState { it.copy(channels = data) }
    }
}

data class PlaylistDetailUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val channels: List<ChannelBO> = emptyList()
): UiState<PlaylistDetailUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): PlaylistDetailUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface PlaylistDetailSideEffects: SideEffect