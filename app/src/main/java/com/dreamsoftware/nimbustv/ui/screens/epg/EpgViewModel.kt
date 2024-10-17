package com.dreamsoftware.nimbustv.ui.screens.epg

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.EpgScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.domain.usecase.DeleteEpgDataUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetEpgDataUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SaveEpgUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpgViewModel @Inject constructor(
    private val saveEpgUseCase: SaveEpgUseCase,
    private val getEpgDataUseCase: GetEpgDataUseCase,
    private val deleteEpgDataUseCase: DeleteEpgDataUseCase,
    @EpgScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<EpgUiState, EpgSideEffects>(), EpgScreenActionListener {

    override fun onGetDefaultState(): EpgUiState = EpgUiState()

    fun fetchData() {
        executeUseCase(
            useCase = getEpgDataUseCase,
            onSuccess = ::onGetEpgDataCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onGetEpgDataCompleted(data: List<EpgDataBO>) {
        updateState { it.copy(epgData = data) }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: EpgUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    override fun onRemoveEpgData() {
        updateState { it.copy(showRemoveEpgData = true) }
    }

    override fun onImportNewEpgData() {

    }

    override fun onRemoveEpgDataConfirmed() {
        executeUseCase(
            useCase = deleteEpgDataUseCase,
            onSuccess = { onEpgDataRemovedSuccessfully() }
        )
    }

    override fun onRemoveEpgDataCancelled() {
        updateState { it.copy(showRemoveEpgData = false) }
    }

    private fun onEpgDataRemovedSuccessfully() {
        updateState {
            it.copy(
                epgData = emptyList(),
                showRemoveEpgData = false
            )
        }
    }
}

data class EpgUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val showRemoveEpgData: Boolean = false,
    val epgData: List<EpgDataBO> = emptyList()
) : UiState<EpgUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): EpgUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface EpgSideEffects : SideEffect