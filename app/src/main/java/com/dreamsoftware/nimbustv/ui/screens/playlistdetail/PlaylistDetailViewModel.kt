package com.dreamsoftware.nimbustv.ui.screens.playlistdetail

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.PlaylistDetailScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.usecase.DeletePlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelsByPlaylistUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistDetailViewModel @Inject constructor(
    private val getChannelsByPlaylistUseCase: GetChannelsByPlaylistUseCase,
    private val deletePlaylistUseCase: DeletePlaylistUseCase,
    @PlaylistDetailScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<PlaylistDetailUiState, PlaylistDetailSideEffects>(), PlaylistDetailScreenActionListener {

    override fun onGetDefaultState(): PlaylistDetailUiState = PlaylistDetailUiState()

    fun fetchData(id: String) {
        updateState { it.copy(playlistId = id) }
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

    override fun onDeletePlaylistClicked() {
        updateState { it.copy(showDeletePlaylistDialog = true) }
    }

    override fun onDeletePlaylistConfirmed() {
        updateState { it.copy(showDeletePlaylistDialog = false) }
        executeUseCaseWithParams(
            useCase = deletePlaylistUseCase,
            params = DeletePlaylistUseCase.Params(
                playlistId = uiState.value.playlistId
            ),
            onSuccess = { onPlayListRemoved() }
        )
    }

    override fun onDeletePlaylistCancelled() {
        updateState { it.copy(showDeletePlaylistDialog = false) }
    }

    private fun onPlayListRemoved() {
        launchSideEffect(PlaylistDetailSideEffects.PlaylistRemovedSideEffect)
    }
}

data class PlaylistDetailUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val playlistId: String = String.EMPTY,
    val channels: List<ChannelBO> = emptyList(),
    val showDeletePlaylistDialog: Boolean = false
): UiState<PlaylistDetailUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): PlaylistDetailUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface PlaylistDetailSideEffects: SideEffect {
    data object PlaylistRemovedSideEffect: PlaylistDetailSideEffects
}