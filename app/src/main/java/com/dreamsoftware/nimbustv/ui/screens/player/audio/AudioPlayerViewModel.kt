package com.dreamsoftware.nimbustv.ui.screens.player.audio

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelByIdUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AudioPlayerViewModel @Inject constructor(
    private val getChannelByIdUseCase: GetChannelByIdUseCase
) : FudgeTvViewModel<AudioPlayerUiState, AudioPlayerSideEffects>() {

    override fun onGetDefaultState(): AudioPlayerUiState = AudioPlayerUiState()

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
                it.copy(
                    title = title.orEmpty(),
                    category = category.orEmpty(),
                    audioUrl = url,
                    id = id,
                    imageUrl = icon.orEmpty(),
                )
            }
        }
    }
}

data class AudioPlayerUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val id: String = String.EMPTY,
    val audioUrl: String = String.EMPTY,
    val licenseKey: String = String.EMPTY,
    val title: String = String.EMPTY,
    val category: String = String.EMPTY,
    val imageUrl: String = String.EMPTY,
): UiState<AudioPlayerUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): AudioPlayerUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface AudioPlayerSideEffects: SideEffect