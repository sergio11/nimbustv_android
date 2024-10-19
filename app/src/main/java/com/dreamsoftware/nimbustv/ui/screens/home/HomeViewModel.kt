package com.dreamsoftware.nimbustv.ui.screens.home

import android.util.Log
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.domain.usecase.AddFavoriteChannelUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CheckFavoriteChannelUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CreatePlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelsByPlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetPlaylistsByProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.RemoveChannelFromFavoritesUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPlaylistsByProfileUseCase: GetPlaylistsByProfileUseCase,
    private val createPlaylistUseCase: CreatePlaylistUseCase,
    private val getChannelsByPlaylistUseCase: GetChannelsByPlaylistUseCase,
    private val addFavoriteChannelUseCase: AddFavoriteChannelUseCase,
    private val removeChannelFromFavoritesUseCase: RemoveChannelFromFavoritesUseCase,
    private val checkFavoriteChannelUseCase: CheckFavoriteChannelUseCase
) : FudgeTvViewModel<HomeUiState, HomeSideEffects>(), HomeScreenActionListener {

    override fun onGetDefaultState(): HomeUiState = HomeUiState()

    fun fetchData() {
        updateState { it.copy(isLoadingPlaylists = true) }
        executeUseCase(
            useCase = getPlaylistsByProfileUseCase,
            onSuccess = ::onGetPlaylistByProfileCompleted
        )
    }

    override fun onImportNewPlaylistClicked() {
        updateState { it.copy(isImportPlaylistDialogVisible = true) }
    }

    override fun onImportNewPlaylistCancelled() {
        resetImportPlaylistState()
    }

    override fun onImportNewPlayListConfirmed() {
        doOnUiState {
            executeUseCaseWithParams(
                useCase = createPlaylistUseCase,
                params = CreatePlaylistUseCase.Params(
                    alias = newPlayListAlias,
                    url = newPlayListUrl
                ),
                onSuccess = { onImportPlayListCompleted() }
            )
        }
    }

    override fun onNewPlayListUrlUpdated(newValue: String) {
        updateState { it.copy(newPlayListUrl = newValue) }
    }

    override fun onNewPlayListAliasUpdated(newValue: String) {
        updateState { it.copy(newPlayListAlias = newValue) }
    }

    override fun onNewPlaylistSelected(newValue: PlayListBO) {
        updateState { it.copy(playlistSelected = newValue) }
        fetchChannelsByPlaylist(playlistId = newValue.id)
    }

    override fun onChannelFocused(value: ChannelBO) {
        updateState { it.copy(channelFocused = value) }
        executeUseCaseWithParams(
            useCase = checkFavoriteChannelUseCase,
            params = CheckFavoriteChannelUseCase.Params(
                channelId = value.id
            ),
            showLoadingState = false,
            onSuccess = ::onCheckFavoriteChannelCompleted
        )
    }

    override fun onChannelPressed(value: ChannelBO) {
        launchSideEffect(HomeSideEffects.PlayChannelSideEffect(id = value.id, type = value.streamTypeEnum))
    }

    override fun onNewCategorySelected(newValue: String) {}

    override fun onManagePlaylistClicked() {
        launchSideEffect(HomeSideEffects.ManagePlaylistSideEffect)
    }

    override fun onAddFavoriteChannelClicked() {
        doOnUiState {
            Log.d("ATV_FAVORITES", "onAddFavoriteChannelClicked CALLED!")
            channelFocused?.id?.let { channelId ->
                Log.d("ATV_FAVORITES", "onAddFavoriteChannelClicked channelId -> $channelId CALLED!")
                executeUseCaseWithParams(
                    useCase = addFavoriteChannelUseCase,
                    params = AddFavoriteChannelUseCase.Params(
                        channelId = channelId
                    ),
                    showLoadingState = false,
                    onSuccess = { onAddFavoriteChannelCompleted() }
                )
            }
        }
    }

    override fun onRemoveChannelFromFavorites() {
        doOnUiState {
            channelFocused?.id?.let { channelId ->
                executeUseCaseWithParams(
                    useCase = removeChannelFromFavoritesUseCase,
                    params = RemoveChannelFromFavoritesUseCase.Params(
                        channelId = channelId
                    ),
                    showLoadingState = false,
                    onSuccess = { onRemoveChannelFromFavoritesCompleted() }
                )
            }
        }
    }

    private fun onGetPlaylistByProfileCompleted(playlists: List<PlayListBO>) {
        val playlistSelected = playlists.firstOrNull()
        updateState {
            it.copy(
                isLoadingPlaylists = false,
                playlists = playlists,
                playlistSelected = playlistSelected
            )
        }
        playlistSelected?.id?.let(::fetchChannelsByPlaylist)
    }

    private fun fetchChannelsByPlaylist(playlistId: String) {
        updateState { it.copy(isLoadingChannels = true) }
        executeUseCaseWithParams(
            useCase = getChannelsByPlaylistUseCase,
            params = GetChannelsByPlaylistUseCase.Params(playlistId = playlistId),
            onSuccess = ::onFetchChannelsByPlaylistCompleted
        )
    }

    private fun onFetchChannelsByPlaylistCompleted(channels: List<ChannelBO>) {
        updateState {
            it.copy(
                isLoadingChannels = false,
                categories = channels.mapNotNull(ChannelBO::category).distinct(),
                channels = channels,
                channelFocused = channels.firstOrNull()
            )
        }
    }

    private fun onImportPlayListCompleted() {
        resetImportPlaylistState()
        fetchData()
    }

    private fun resetImportPlaylistState() {
        updateState {
            it.copy(
                isImportPlaylistDialogVisible = false,
                newPlayListUrl = String.EMPTY,
                newPlayListAlias = String.EMPTY
            )
        }
    }

    private fun onCheckFavoriteChannelCompleted(isFavoriteChannel: Boolean) {
        updateState { it.copy(isFavoriteChannel = isFavoriteChannel) }
    }

    private fun onAddFavoriteChannelCompleted() {
        Log.d("ATV_FAVORITES", "onAddFavoriteChannelCompleted isFavoriteChannel = true CALLED!")
        updateState { it.copy(isFavoriteChannel = true) }
    }

    private fun onRemoveChannelFromFavoritesCompleted() {
        Log.d("ATV_FAVORITES", "onRemoveChannelFromFavoritesCompleted isFavoriteChannel = false CALLED!")
        updateState { it.copy(isFavoriteChannel = false) }
    }
}

data class HomeUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val isLoadingPlaylists: Boolean = false,
    val isLoadingChannels: Boolean = false,
    val channels: List<ChannelBO> = emptyList(),
    val isImportPlaylistDialogVisible: Boolean = false,
    val newPlayListAlias: String = String.EMPTY,
    val newPlayListUrl: String = String.EMPTY,
    val playlists: List<PlayListBO> = emptyList(),
    val playlistSelected: PlayListBO? = null,
    val categories: List<String> = emptyList(),
    val categorySelected: String? = null,
    val channelFocused: ChannelBO? = null,
    val isFavoriteChannel: Boolean = false
) : UiState<HomeUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): HomeUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface HomeSideEffects : SideEffect {
    data class PlayChannelSideEffect(val id: String, val type: StreamTypeEnum) : HomeSideEffects
    data object ManagePlaylistSideEffect: HomeSideEffects
}