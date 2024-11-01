package com.dreamsoftware.nimbustv.ui.screens.player.video

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.usecase.AddFavoriteChannelUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CheckFavoriteChannelUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelByIdUseCase
import com.dreamsoftware.nimbustv.domain.usecase.RemoveChannelFromFavoritesUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getChannelByIdUseCase: GetChannelByIdUseCase,
    private val addFavoriteChannelUseCase: AddFavoriteChannelUseCase,
    private val removeChannelFromFavoritesUseCase: RemoveChannelFromFavoritesUseCase,
    private val checkFavoriteChannelUseCase: CheckFavoriteChannelUseCase
) : FudgeTvViewModel<VideoPlayerUiState, VideoPlayerSideEffects>(), VideoPlayerScreenActionListener {

    override fun onGetDefaultState(): VideoPlayerUiState = VideoPlayerUiState()

    fun fetchData(id: String) {
        fetchChannelDetail(channelId = id)
        checkFavoriteChannel(channelId = id)
    }

    override fun onAddFavoriteChannelClicked() {
        doOnUiState {
            executeUseCaseWithParams(
                useCase = addFavoriteChannelUseCase,
                params = AddFavoriteChannelUseCase.Params(
                    channelId = id
                ),
                showLoadingState = false,
                onSuccess = { onAddFavoriteChannelCompleted() }
            )
        }
    }

    override fun onRemoveChannelFromFavorites() {
        doOnUiState {
            executeUseCaseWithParams(
                useCase = removeChannelFromFavoritesUseCase,
                params = RemoveChannelFromFavoritesUseCase.Params(
                    channelId = id
                ),
                showLoadingState = false,
                onSuccess = { onRemoveChannelFromFavoritesCompleted() }
            )
        }
    }

    override fun onOpenSettingsClicked() {
        launchSideEffect(VideoPlayerSideEffects.OpenSettingsSideEffects)
    }

    private fun fetchChannelDetail(channelId: String) {
        executeUseCaseWithParams(
            useCase = getChannelByIdUseCase,
            params = GetChannelByIdUseCase.Params(channelId),
            onSuccess = ::onGetChannelByIdSuccessfully
        )
    }

    private fun onGetChannelByIdSuccessfully(channel: ChannelBO) {
        updateState {
            with(channel) {
                it.copy(
                    title = title.orEmpty(),
                    subtitle = category.orEmpty(),
                    videoUrl = url,
                    licenseKey = licenseKey.orEmpty(),
                    channelLogo = icon.orEmpty(),
                    id = id
                )
            }
        }
    }

    private fun checkFavoriteChannel(channelId: String) {
        executeUseCaseWithParams(
            useCase = checkFavoriteChannelUseCase,
            params = CheckFavoriteChannelUseCase.Params(
                channelId =channelId
            ),
            showLoadingState = false,
            onSuccess = ::onCheckFavoriteChannelCompleted
        )
    }

    private fun onCheckFavoriteChannelCompleted(isFavoriteChannel: Boolean) {
        updateState { it.copy(isFavoriteChannel = isFavoriteChannel) }
    }

    private fun onAddFavoriteChannelCompleted() {
        updateState { it.copy(isFavoriteChannel = true) }
    }

    private fun onRemoveChannelFromFavoritesCompleted() {
        updateState { it.copy(isFavoriteChannel = false) }
    }
}

data class VideoPlayerUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val id: String = String.EMPTY,
    val videoUrl: String = String.EMPTY,
    val licenseKey: String = String.EMPTY,
    val channelLogo: String = String.EMPTY,
    val title: String = String.EMPTY,
    val subtitle: String = String.EMPTY,
    val isFavoriteChannel: Boolean = false
): UiState<VideoPlayerUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): VideoPlayerUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface VideoPlayerSideEffects: SideEffect {
    data object OpenSettingsSideEffects: VideoPlayerSideEffects
}