package com.dreamsoftware.nimbustv.ui.screens.epg

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.EpgScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO
import com.dreamsoftware.nimbustv.domain.model.EpgViewModeEnum
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO
import com.dreamsoftware.nimbustv.domain.usecase.DeleteEpgDataUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetEpgDataUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetUserPreferencesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SaveEpgUseCase
import com.dreamsoftware.nimbustv.ui.screens.epg.extension.filterSchedulesByChannel
import com.dreamsoftware.nimbustv.ui.screens.epg.extension.mapToChannelOverviewList
import com.dreamsoftware.nimbustv.ui.screens.epg.extension.mapToLiveScheduleList
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ChannelOverviewVO
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ScheduleVO
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class EpgViewModel @Inject constructor(
    private val saveEpgUseCase: SaveEpgUseCase,
    private val getEpgDataUseCase: GetEpgDataUseCase,
    private val deleteEpgDataUseCase: DeleteEpgDataUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    @EpgScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<EpgUiState, EpgSideEffects>(), EpgScreenActionListener {

    private var epgData: List<ChannelEpgDataBO> = emptyList()

    override fun onGetDefaultState(): EpgUiState = EpgUiState()

    fun fetchData() {
        fetchEgpData()
        fetchUserPreferences()
    }

    private fun onGetEpgDataCompleted(data: List<ChannelEpgDataBO>) {
        resetState(data)
    }

    private fun onMapExceptionToState(ex: Exception, uiState: EpgUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    override fun onRemoveEpgData() {
        updateState { it.copy(showRemoveEpgDataDialog = true) }
    }

    override fun onImportNewEpgData() {
        updateState { it.copy(showImportEpgDataDialog = true) }
    }

    override fun onImportNewEpgDataConfirmed() {
        updateState { it.copy(showImportEpgDataDialog = false) }
        doOnUiState {
            executeUseCaseWithParams(
                useCase = saveEpgUseCase,
                params = SaveEpgUseCase.Params(url = newEpgDataUrl),
                onSuccess = ::onImportEpgDataCompleted
            )
        }
    }

    override fun onImportNewEpgDataCancelled() {
        updateState { it.copy(showImportEpgDataDialog = false) }
    }

    override fun onRemoveEpgDataConfirmed() {
        executeUseCase(
            useCase = deleteEpgDataUseCase,
            onSuccess = { onEpgDataRemovedSuccessfully() }
        )
    }

    override fun onRemoveEpgDataCancelled() {
        updateState { it.copy(showRemoveEpgDataDialog = false) }
    }

    override fun onNewEpgDataUrlUpdated(newValue: String) {
        updateState { it.copy(newEpgDataUrl = newValue) }
    }

    override fun onOpenEpgChannel(channelId: String) {
        updateState {
            it.copy(
                channelSelectedId = channelId,
                currentChannelSchedules = epgData.filterSchedulesByChannel(channelId)
            )
        }
    }

    override fun onOpenScheduleDetail(schedule: ScheduleVO) {
        updateState { it.copy(scheduleSelected = schedule) }
    }

    override fun onCloseScheduleDetail() {
        updateState { it.copy(scheduleSelected = null) }
    }

    private fun onEpgDataRemovedSuccessfully() {
        epgData = emptyList()
        updateState {
            it.copy(
                channelOverviewList = emptyList(),
                liveSchedules = emptyList(),
                currentChannelSchedules = emptyList(),
                showRemoveEpgDataDialog = false
            )
        }
    }

    private fun onImportEpgDataCompleted(data: List<ChannelEpgDataBO>) {
        resetState(data)
    }
    
    private fun onGetUserPreferencesCompleted(userPreferenceBO: UserPreferenceBO) {
        updateState { it.copy(epgViewMode = userPreferenceBO.epgViewMode) }
    }

    private fun resetState(data: List<ChannelEpgDataBO>) {
        epgData = data
        val channelId = data.firstOrNull()?.channelId.orEmpty()
        updateState {
            if(it.epgViewMode == EpgViewModeEnum.NOW_AND_SCHEDULE) {
                it.copy(
                    channelSelectedId = channelId,
                    liveSchedules = data.mapToLiveScheduleList(),
                    currentChannelSchedules = data.filterSchedulesByChannel(channelId)
                )
            } else {
                it.copy(
                    channelSelectedId = channelId,
                    channelOverviewList = data.mapToChannelOverviewList()
                )
            }
        }
    }

    private fun fetchEgpData() {
        executeUseCase(
            useCase = getEpgDataUseCase,
            onSuccess = ::onGetEpgDataCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun fetchUserPreferences() {
        executeUseCase(
            useCase = getUserPreferencesUseCase,
            showLoadingState = false,
            onSuccess = ::onGetUserPreferencesCompleted
        )
    }
}

data class EpgUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val showRemoveEpgDataDialog: Boolean = false,
    val showImportEpgDataDialog: Boolean = false,
    val epgViewMode: EpgViewModeEnum = EpgViewModeEnum.NOW_AND_SCHEDULE,
    val channelOverviewList: List<ChannelOverviewVO> = emptyList(),
    val channelSelectedId: String = String.EMPTY,
    val newEpgDataUrl: String = String.EMPTY,
    val liveSchedules: List<ScheduleVO> = emptyList(),
    val currentChannelSchedules: List<ScheduleVO> = emptyList(),
    val scheduleSelected: ScheduleVO? = null,
) : UiState<EpgUiState>(isLoading, errorMessage) {

    val epgDataIsEmpty: Boolean
            get() = liveSchedules.isEmpty() && channelOverviewList.isEmpty()

    override fun copyState(isLoading: Boolean, errorMessage: String?): EpgUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}

sealed interface EpgSideEffects : SideEffect