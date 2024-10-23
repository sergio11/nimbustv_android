package com.dreamsoftware.nimbustv.ui.screens.search

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.FavoritesScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.domain.usecase.RemoveChannelFromFavoritesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SearchChannelsUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import com.dreamsoftware.nimbustv.ui.utils.SPACE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val removeChannelFromFavoritesUseCase: RemoveChannelFromFavoritesUseCase,
    private val searchChannelsUseCase: SearchChannelsUseCase,
    @FavoritesScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<SearchUiState, SearchSideEffects>(), SearchScreenActionListener {

    companion object {
        private const val SEARCH_DELAY_MILLIS: Long = 3000
    }

    private var searchJob: Job? = null

    override fun onGetDefaultState(): SearchUiState = SearchUiState()

    fun fetchData() {
        launchSearch()
    }

    override fun onKeyPressed(key: String) {
        doOnUiState { onTermUpdated(newTerm = term.plus(key)) }
        launchSearchAfterDelay()
    }

    override fun onClearPressed() {
        onTermUpdated(newTerm = String.EMPTY)
    }

    override fun onBackSpacePressed() {
        doOnUiState { onTermUpdated(newTerm = term.dropLast(1)) }
        launchSearchAfterDelay()
    }

    override fun onSpaceBarPressed() {
        doOnUiState { onTermUpdated(newTerm = term.plus(Char.SPACE)) }
        launchSearchAfterDelay()
    }

    override fun onSearchPressed() {
        launchSearch()
    }

    private fun launchSearchAfterDelay() {
        searchJob?.cancel()
        viewModelScope.launch {
            delay(SEARCH_DELAY_MILLIS)
            launchSearch()
        }.also {
            searchJob = it
        }
    }

    private fun launchSearch() {
        doOnUiState {
            if (term.isNotBlank()) {
                onSearch()
            }
        }
    }

    private fun onSearch() {
        doOnUiState {
            executeUseCaseWithParams(
                useCase = searchChannelsUseCase,
                params = SearchChannelsUseCase.Params(term = term),
                onSuccess = ::onSearchCompletedSuccessfully,
            )
        }
    }

    private fun onTermUpdated(newTerm: String) {
        updateState { it.copy(term = newTerm) }
    }

    private fun onSearchCompletedSuccessfully(channels: List<ChannelBO>) {
        updateState { it.copy(channels = channels) }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SearchUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

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