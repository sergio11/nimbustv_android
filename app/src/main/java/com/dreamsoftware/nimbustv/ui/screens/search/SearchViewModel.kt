package com.dreamsoftware.nimbustv.ui.screens.search

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.SearchChannelsScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.domain.usecase.AddFavoriteChannelUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CheckFavoriteChannelUseCase
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
    private val addFavoriteChannelUseCase: AddFavoriteChannelUseCase,
    private val removeChannelFromFavoritesUseCase: RemoveChannelFromFavoritesUseCase,
    private val searchChannelsUseCase: SearchChannelsUseCase,
    private val checkFavoriteChannelUseCase: CheckFavoriteChannelUseCase,
    @SearchChannelsScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
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

    override fun onOpenChannelDetail(channel: ChannelBO) {
        updateState { it.copy(channelSelected = channel) }
        executeUseCaseWithParams(
            useCase = checkFavoriteChannelUseCase,
            showLoadingState = false,
            params = CheckFavoriteChannelUseCase.Params(
                channelId = channel.id
            ),
            onSuccess = ::onVerifyFavoriteChannelCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
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

    override fun onAddToFavorites(channel: ChannelBO) {
        executeUseCaseWithParams(
            useCase = addFavoriteChannelUseCase,
            showLoadingState = false,
            params = AddFavoriteChannelUseCase.Params(
                channelId = channel.id
            ),
            onSuccess = { onAddChannelToFavoritesCompleted() },
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    override fun onRemoveFromFavorites(channel: ChannelBO) {
        executeUseCaseWithParams(
            useCase = removeChannelFromFavoritesUseCase,
            showLoadingState = false,
            params = RemoveChannelFromFavoritesUseCase.Params(
                channelId = channel.id
            ),
            onSuccess = { onRemoveChannelFromFavoritesCompleted() },
            onMapExceptionToState = ::onMapExceptionToState
        )
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
                onMapExceptionToState = ::onMapExceptionToState,
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

    private fun onAddChannelToFavoritesCompleted() {
        updateState { it.copy(isFavoriteChannel = true) }
    }

    private fun onRemoveChannelFromFavoritesCompleted() {
        updateState { it.copy(isFavoriteChannel = false) }
    }

    private fun onVerifyFavoriteChannelCompleted(isFavoriteChannel: Boolean) {
        updateState { it.copy(isFavoriteChannel = isFavoriteChannel) }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: SearchUiState) =
        uiState.copy(
            channelSelected = null,
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )
}

data class SearchUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val channels: List<ChannelBO> = emptyList(),
    val term: String = String.EMPTY,
    val channelSelected: ChannelBO? = null,
    val isFavoriteChannel: Boolean = false
): UiState<SearchUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): SearchUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface SearchSideEffects: SideEffect {
    data class PlayChannelSideEffect(val channelId: String, val streamType: StreamTypeEnum): SearchSideEffects
}