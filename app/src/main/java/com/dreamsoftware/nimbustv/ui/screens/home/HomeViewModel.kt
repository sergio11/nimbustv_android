package com.dreamsoftware.nimbustv.ui.screens.home

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.domain.usecase.CreatePlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelsByPlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetPlaylistsByProfileUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlaylistsByProfileUseCase: GetPlaylistsByProfileUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val getChannelsByPlaylistUseCase: GetChannelsByPlaylistUseCase
) : FudgeTvViewModel<HomeUiState, HomeSideEffects>(), HomeScreenActionListener {

    override fun onGetDefaultState(): HomeUiState = HomeUiState()

    fun fetchData() {
        executeUseCase(
            useCase = getPlaylistsByProfileUseCase,
            onSuccess = ::onGetPlaylistByProfileCompleted
        )
    }

    private fun onImportPlaylistCompleted() {

    }

    override fun onImportNewPlaylistClicked() {
        updateState { it.copy(isImportPlaylistDialogVisible = true) }
    }

    override fun onImportNewPlaylistCancelled() {
        updateState {
            it.copy(
                isImportPlaylistDialogVisible = false,
                newPlayListUrl = String.EMPTY
            )
        }
    }

    override fun onNewPlayListUrlUpdated(newValue: String) {
        updateState { it.copy(newPlayListUrl = newValue) }
    }

    override fun onNewPlaylistSelected(newValue: PlayListBO) {
        updateState { it.copy(playlistSelected = newValue) }
        fetchChannelsByPlaylist(playlistId = newValue.id)
    }

    override fun onChannelFocused(value: ChannelBO) {
        updateState { it.copy(channelFocused = value) }
    }

    override fun onChannelPressed(value: ChannelBO) {
        launchSideEffect(HomeSideEffects.PlayChannelSideEffect(id = value.id, type = value.streamTypeEnum))
    }

    override fun onNewCategorySelected(newValue: String) {}

    private fun onGetPlaylistByProfileCompleted(playlists: List<PlayListBO>) {
        val playlistSelected = playlists.firstOrNull()
        updateState {
            it.copy(
                playlists = playlists,
                playlistSelected = playlistSelected
            )
        }
        playlistSelected?.id?.let(::fetchChannelsByPlaylist)
    }

    private fun fetchChannelsByPlaylist(playlistId: String) {
        executeUseCaseWithParams(
            useCase = getChannelsByPlaylistUseCase,
            params = GetChannelsByPlaylistUseCase.Params(playlistId = playlistId),
            onSuccess = ::onFetchChannelsByPlaylistCompleted
        )
    }

    private fun onFetchChannelsByPlaylistCompleted(channels: List<ChannelBO>) {
        updateState {
            it.copy(
                categories = channels.mapNotNull(ChannelBO::category).distinct(),
                channels = channels,
                channelFocused = channels.firstOrNull()
            )
        }
    }
}

data class HomeUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val channels: List<ChannelBO> = emptyList(),
    val isImportPlaylistDialogVisible: Boolean = false,
    val isImporting: Boolean = false,
    val newPlayListUrl: String = String.EMPTY,
    val playlists: List<PlayListBO> = emptyList(),
    val playlistSelected: PlayListBO? = null,
    val categories: List<String> = emptyList(),
    val categorySelected: String? = null,
    val channelFocused: ChannelBO? = null
) : UiState<HomeUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): HomeUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface HomeSideEffects : SideEffect {
    data class PlayChannelSideEffect(val id: String, val type: StreamTypeEnum) : HomeSideEffects
}