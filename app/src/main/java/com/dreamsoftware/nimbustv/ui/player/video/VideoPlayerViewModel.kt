package com.dreamsoftware.nimbustv.ui.player.video

import android.util.Log
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelByIdUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class VideoPlayerViewModel @Inject constructor(
    private val getChannelByIdUseCase: GetChannelByIdUseCase
) : FudgeTvViewModel<VideoPlayerUiState, VideoPlayerSideEffects>() {

    override fun onGetDefaultState(): VideoPlayerUiState = VideoPlayerUiState()

    fun fetchData(id: String) {
        executeUseCaseWithParams(
            useCase = getChannelByIdUseCase,
            params = GetChannelByIdUseCase.Params(id),
            onSuccess = ::onGetChannelByIdSuccessfully
        )
    }

    private fun onGetChannelByIdSuccessfully(channel: ChannelBO) {
        updateState {
            with(channel) {
                Log.d("ATV_VIDEO_PLAYER", "onGetChannelByIdSuccessfully - url: $url")
                it.copy(
                    title = title.orEmpty(),
                    subtitle = category.orEmpty(),
                    videoUrl = url,
                    channelLogo = icon.orEmpty(),
                    id = id
                )
            }
        }
    }
}

data class VideoPlayerUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val id: String = String.EMPTY,
    val videoUrl: String = String.EMPTY,
    val channelLogo: String = String.EMPTY,
    val title: String = String.EMPTY,
    val subtitle: String = String.EMPTY
): UiState<VideoPlayerUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): VideoPlayerUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface VideoPlayerSideEffects: SideEffect