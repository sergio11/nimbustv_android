package com.dreamsoftware.nimbustv.ui.screens.epgsources

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.EpgSourcesScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.domain.usecase.CreateEpgUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetEpgListUseCase
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpgSourcesViewModel @Inject constructor(
    private val getEpgListUseCase: GetEpgListUseCase,
    private val createEpgUseCase: CreateEpgUseCase,
    @EpgSourcesScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<EpgSourcesUiState, EpgSourcesSideEffects>(), EpgSourcesScreenActionListener {

    override fun onGetDefaultState(): EpgSourcesUiState = EpgSourcesUiState()

    fun fetchData() {
        executeUseCase(
            useCase = getEpgListUseCase,
            onSuccess = ::onFetchEpgSourcesSuccessfully,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun onFetchEpgSourcesSuccessfully(data: List<EpgBO>) {
        updateState { it.copy(epgSources = data) }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: EpgSourcesUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    override fun onImportNewEpgClicked() {
        updateState { it.copy(isImportEpgDialogVisible = true) }
    }

    override fun onImportNewEpgCancelled() {
        resetImportEpgState()
    }

    override fun onImportNewEpgConfirmed() {
        updateState { it.copy(isImporting = true) }
        doOnUiState {
            executeUseCaseWithParams(
                useCase = createEpgUseCase,
                showLoadingState = false,
                params = CreateEpgUseCase.Params(
                    alias = newEpgAlias,
                    url = newEpgUrl
                ),
                onSuccess = { onImportEpgCompleted() }
            )
        }
    }

    override fun onNewEpgUrlUpdated(newValue: String) {
        updateState { it.copy(newEpgUrl = newValue) }
    }

    override fun onNewEpgAliasUpdated(newValue: String) {
        updateState { it.copy(newEpgAlias = newValue) }
    }

    private fun onImportEpgCompleted() {
        resetImportEpgState()
        fetchData()
    }

    private fun resetImportEpgState() {
        updateState {
            it.copy(
                isImporting = false,
                isImportEpgDialogVisible = false,
                newEpgUrl = String.EMPTY,
                newEpgAlias = String.EMPTY
            )
        }
    }
}

data class EpgSourcesUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val epgSources: List<EpgBO> = emptyList(),
    val isImportEpgDialogVisible: Boolean = false,
    val isImporting: Boolean = false,
    val newEpgAlias: String = String.EMPTY,
    val newEpgUrl: String = String.EMPTY,
): UiState<EpgSourcesUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): EpgSourcesUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface EpgSourcesSideEffects: SideEffect