package com.dreamsoftware.nimbustv.ui.screens.search

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.FavoritesScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.domain.usecase.GetFavoriteChannelsByProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.RemoveChannelFromFavoritesUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val removeChannelFromFavoritesUseCase: RemoveChannelFromFavoritesUseCase,
    private val getFavoriteChannelsByProfileUseCase: GetFavoriteChannelsByProfileUseCase,
    @FavoritesScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<SearchUiState, SearchSideEffects>(), SearchScreenActionListener {

    override fun onGetDefaultState(): SearchUiState = SearchUiState()

    fun fetchData() {
        executeUseCase(
            useCase = getFavoriteChannelsByProfileUseCase,
            onSuccess = ::onGetFavoritesChannelsByProfileCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SearchUiState) =
        uiState.copy(
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
        launchSideEffect(SearchSideEffects.PlayChannelSideEffect(
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
            onSuccess = { onRemoveChannelFromFavoritesCompleted(channel) }
        )
    }

    private fun onRemoveChannelFromFavoritesCompleted(channelRemoved: ChannelBO) {
        updateState { it.copy(channels = it.channels.filter { channel -> channel.id != channelRemoved.id }) }
    }
}

data class SearchUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val channels: List<ChannelBO> = emptyList(),
    val term: String = String.EMPTY,
    val channelSelected: ChannelBO? = null
): UiState<SearchUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SearchUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SearchSideEffects: SideEffect {
    data class PlayChannelSideEffect(val channelId: String, val streamType: StreamTypeEnum): SearchSideEffects
}