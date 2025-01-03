package com.dreamsoftware.nimbustv.ui.screens.playlists

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.PlaylistsScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.domain.usecase.CreatePlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetPlaylistsByProfileUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class PlaylistsViewModel @Inject constructor(
    private val getPlaylistsByProfileUseCase: GetPlaylistsByProfileUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
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

    override fun onImportNewPlaylistClicked() {
        updateState { it.copy(isImportPlaylistDialogVisible = true) }
    }

    override fun onImportNewPlaylistCancelled() {
        resetImportPlaylistState()
    }

    override fun onImportNewPlayListConfirmed() {
        updateState { it.copy(isImporting = true) }
        doOnUiState {
            executeUseCaseWithParams(
                useCase = createPlaylistUseCase,
                params = CreatePlaylistUseCase.Params(
                    alias = newPlayListAlias,
                    url = newPlayListUrl
                ),
                onSuccess = { onImportPlayListCompleted() },
                onMapExceptionToState = ::onMapExceptionToState
            )
        }
    }

    override fun onNewPlayListUrlUpdated(newValue: String) {
        updateState { it.copy(newPlayListUrl = newValue) }
    }

    override fun onNewPlayListAliasUpdated(newValue: String) {
        updateState { it.copy(newPlayListAlias = newValue) }
    }

    override fun onOpenPlaylistDetailClicked(playListBO: PlayListBO) {
        launchSideEffect(PlaylistsSideEffects.OpenPlaylistDetailSideEffect(playListBO.id))
    }

    private fun onImportPlayListCompleted() {
        resetImportPlaylistState()
        fetchData()
    }

    private fun resetImportPlaylistState() {
        updateState {
            it.copy(
                isImporting = false,
                isImportPlaylistDialogVisible = false,
                newPlayListUrl = String.EMPTY,
                newPlayListAlias = String.EMPTY
            )
        }
    }

    private fun onFetchPlaylistsSuccessfully(data: List<PlayListBO>) {
        updateState { it.copy(playlists = data) }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: PlaylistsUiState) =
        uiState.copy(
            isImportPlaylistDialogVisible = false,
            isImporting = false,
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class PlaylistsUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val isImporting: Boolean = false,
    val playlists: List<PlayListBO> = emptyList(),
    val isImportPlaylistDialogVisible: Boolean = false,
    val newPlayListAlias: String = String.EMPTY,
    val newPlayListUrl: String = String.EMPTY,
): UiState<PlaylistsUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): PlaylistsUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface PlaylistsSideEffects: SideEffect {
    data class OpenPlaylistDetailSideEffect(val playListId: String): PlaylistsSideEffects
}