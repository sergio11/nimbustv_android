package com.dreamsoftware.nimbustv.ui.screens.favorites

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.FavoritesScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.domain.usecase.GetFavoriteChannelsByProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.RemoveChannelFromFavoritesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
    private val removeChannelFromFavoritesUseCase: RemoveChannelFromFavoritesUseCase,
    private val getFavoriteChannelsByProfileUseCase: GetFavoriteChannelsByProfileUseCase,
    @FavoritesScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<FavoritesUiState, FavoritesSideEffects>(), FavoritesScreenActionListener {

    override fun onGetDefaultState(): FavoritesUiState = FavoritesUiState()

    fun fetchData() {
        executeUseCase(
            useCase = getFavoriteChannelsByProfileUseCase,
            onSuccess = ::onGetFavoritesChannelsByProfileCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onMapExceptionToState(ex: Exception, uiState: FavoritesUiState) =
        uiState.copy(
            channelSelected = null,
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    private fun onGetFavoritesChannelsByProfileCompleted(channels: List<ChannelBO>) {
        updateState { it.copy(channels = channels) }
    }

    override fun onOpenChannelDetail(channel: ChannelBO) {
        updateState { it.copy(channelSelected = channel) }
    }

    override fun onCloseDetail() {
        updateState { it.copy(channelSelected = null) }
    }

    override fun onPlayChannel(channel: ChannelBO) {
        updateState { it.copy(channelSelected = null) }
        launchSideEffect(FavoritesSideEffects.PlayChannelSideEffect(
            channelId = channel.id,
            streamType = channel.streamTypeEnum
        ))
    }

    override fun onRemoveFromFavorites(channel: ChannelBO) {
        updateState { it.copy(channelSelected = null) }
        executeUseCaseWithParams(
            useCase = removeChannelFromFavoritesUseCase,
            params = RemoveChannelFromFavoritesUseCase.Params(
                channelId = channel.id
            ),
            onSuccess = { onRemoveChannelFromFavoritesCompleted(channel) },
            onMapExceptionToState = ::onMapExceptionToState,
        )
    }

    private fun onRemoveChannelFromFavoritesCompleted(channelRemoved: ChannelBO) {
        updateState { it.copy(channels = it.channels.filter { channel -> channel.id != channelRemoved.id }) }
    }
}

data class FavoritesUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val channels: List<ChannelBO> = emptyList(),
    val channelSelected: ChannelBO? = null
): UiState<FavoritesUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): FavoritesUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface FavoritesSideEffects: SideEffect {
    data class PlayChannelSideEffect(val channelId: String, val streamType: StreamTypeEnum): FavoritesSideEffects
}