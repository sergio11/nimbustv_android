package com.dreamsoftware.nimbustv.ui.screens.favorites

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.FavoritesScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.usecase.GetFavoriteChannelsByProfileUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class FavoritesViewModel @Inject constructor(
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
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    private fun onGetFavoritesChannelsByProfileCompleted(channels: List<ChannelBO>) {
        updateState { it.copy(channels = channels) }
    }
}

data class FavoritesUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val channels: List<ChannelBO> = emptyList()
): UiState<FavoritesUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): FavoritesUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface FavoritesSideEffects: SideEffect