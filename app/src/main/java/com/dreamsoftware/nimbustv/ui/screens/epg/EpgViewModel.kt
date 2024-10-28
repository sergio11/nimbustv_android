package com.dreamsoftware.nimbustv.ui.screens.epg

import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.nimbustv.di.EpgScreenErrorMapper
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.EpgViewModeEnum
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO
import com.dreamsoftware.nimbustv.domain.usecase.GetEpgDataByIdUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetEpgListUseCase
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
    private val getEpgDataByIdUseCase: GetEpgDataByIdUseCase,
    private val getEpgListUseCase: GetEpgListUseCase,
    private val getUserPreferencesUseCase: GetUserPreferencesUseCase,
    @EpgScreenErrorMapper private val errorMapper: IFudgeTvErrorMapper,
) : FudgeTvViewModel<EpgUiState, EpgSideEffects>(), EpgScreenActionListener {

    private var epgSelectedData: List<EpgChannelBO> = emptyList()

    override fun onGetDefaultState(): EpgUiState = EpgUiState()

    fun fetchData() {
        fetchEgpListByProfile()
        fetchUserPreferences()
    }

    override fun onNewEpgSelected(newValue: EpgBO) {
        updateState { it.copy(epgSelected = newValue) }
        fetchEpgDataById(newValue.id)
    }

    override fun onImportNewEpgData() {
        updateState { it.copy(showImportEpgDataDialog = true) }
    }

    override fun onImportNewEpgDataConfirmed() {
        updateState { it.copy(isImporting = true) }
        doOnUiState {
            executeUseCaseWithParams(
                useCase = saveEpgUseCase,
                showLoadingState = false,
                params = SaveEpgUseCase.Params(
                    alias = newEpgAlias,
                    url = newEpgDataUrl
                ),
                onSuccess = ::onImportEpgDataCompleted
            )
        }
    }

    override fun onImportNewEpgDataCancelled() {
        updateState { it.copy(showImportEpgDataDialog = false) }
    }

    override fun onNewEpgAliasUpdated(newValue: String) {
        updateState { it.copy(newEpgAlias = newValue) }
    }

    override fun onNewEpgUrlUpdated(newValue: String) {
        updateState { it.copy(newEpgDataUrl = newValue) }
    }

    override fun onOpenEpgChannel(channelId: String) {
        updateState {
            it.copy(
                channelSelectedId = channelId,
                currentChannelSchedules = epgSelectedData.filterSchedulesByChannel(channelId)
            )
        }
    }

    override fun onOpenScheduleDetail(schedule: ScheduleVO) {
        updateState { it.copy(scheduleSelected = schedule) }
    }

    override fun onCloseScheduleDetail() {
        updateState { it.copy(scheduleSelected = null) }
    }

    private fun onMapExceptionToState(ex: Exception, uiState: EpgUiState) =
        uiState.copy(
            isLoading = false,
            errorMessage = errorMapper.mapToMessage(ex)
        )

    private fun onImportEpgDataCompleted(data: EpgBO) {
        updateState {
            it.copy(
                isImporting = false,
                showImportEpgDataDialog = false
            )
        }
        onGetEpgListCompleted(listOf(data))
    }
    
    private fun onGetUserPreferencesCompleted(userPreferenceBO: UserPreferenceBO) {
        updateState { it.copy(epgViewMode = userPreferenceBO.epgViewMode) }
    }

    private fun onGetEpgListCompleted(data: List<EpgBO>) {
        val epgSelected = data.firstOrNull()
        updateState {
            it.copy(
                epgList = data,
                epgSelected = epgSelected
            )
        }
        epgSelected?.id?.let(::fetchEpgDataById)
    }

    private fun onGetEpgDataCompleted(data: List<EpgChannelBO>) {
        resetState(data)
    }

    private fun fetchEgpListByProfile() {
        executeUseCase(
            useCase = getEpgListUseCase,
            onSuccess = ::onGetEpgListCompleted,
            onMapExceptionToState = ::onMapExceptionToState
        )
    }

    private fun fetchEpgDataById(epgId: String) {
        executeUseCaseWithParams(
            useCase = getEpgDataByIdUseCase,
            params = GetEpgDataByIdUseCase.Params(epgId),
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

    private fun resetState(data: List<EpgChannelBO>) {
        epgSelectedData = data
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
}

data class EpgUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val showImportEpgDataDialog: Boolean = false,
    val isImporting: Boolean = false,
    val epgList: List<EpgBO> = emptyList(),
    val epgSelected: EpgBO? = null,
    val epgViewMode: EpgViewModeEnum = EpgViewModeEnum.NOW_AND_SCHEDULE,
    val channelOverviewList: List<ChannelOverviewVO> = emptyList(),
    val channelSelectedId: String = String.EMPTY,
    val newEpgAlias: String = String.EMPTY,
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