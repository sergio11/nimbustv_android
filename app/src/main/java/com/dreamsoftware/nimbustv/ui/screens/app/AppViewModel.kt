package com.dreamsoftware.nimbustv.ui.screens.app

import androidx.lifecycle.viewModelScope
import com.dreamsoftware.nimbustv.AppEvent
import com.dreamsoftware.fudge.core.FudgeTvViewModel
import com.dreamsoftware.fudge.core.SideEffect
import com.dreamsoftware.fudge.core.UiState
import com.dreamsoftware.fudge.utils.FudgeTvEventBus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import java.time.LocalDateTime
import javax.inject.Inject

@HiltViewModel
class AppViewModel @Inject constructor(
    private val appEventBus: FudgeTvEventBus
) : FudgeTvViewModel<AppUiState, AppSideEffects>(), IAppScreenActionListener {

    init {
        observeEvents()
    }

    override fun onGetDefaultState(): AppUiState = AppUiState()

    private fun observeEvents() {
        viewModelScope.launch {
            appEventBus.events.collect { event ->
                when (event) {
                    is AppEvent.ComeFromStandby -> launchSideEffect(AppSideEffects.ComeFromStandby)
                    is AppEvent.SignOff -> launchSideEffect(AppSideEffects.NoSessionActive)
                    is AppEvent.NetworkConnectivityStateChanged ->
                        onNetworkConnectivityChanged(event.newState)
                    is AppEvent.ScheduleReminderFired -> onScheduleReminderFired(event)
                    AppEvent.GoToStandby -> {}
                }
            }
        }
    }

    private fun onNetworkConnectivityChanged(newState: Boolean) {
        updateState {
            it.copy(hasNetworkConnectivity = newState)
        }
    }

    private fun onScheduleReminderFired(event: AppEvent.ScheduleReminderFired) {
        updateState {
            it.copy(
                scheduleReminderFired = with(event) {
                    ScheduleReminderFiredVO(
                        title = title,
                        startTime = startTime,
                        endTime = endTime,
                    )
                }
            )
        }
    }

    override fun onOpenSettingsPressed() {
        launchSideEffect(AppSideEffects.OpenSettings)
    }

    override fun onRestartAppPressed() {
        launchSideEffect(AppSideEffects.RestartApp)
    }

    override fun onScheduleReminderAccepted() {
        updateState { it.copy(scheduleReminderFired = null) }
    }
}

data class AppUiState(
    override val isLoading: Boolean = false,
    override val errorMessage: String? = null,
    val hasNetworkConnectivity: Boolean = true,
    val scheduleReminderFired: ScheduleReminderFiredVO? = null
) : UiState<AppUiState>(isLoading, errorMessage) {
    override fun copyState(isLoading: Boolean, errorMessage: String?): AppUiState =
        copy(isLoading = isLoading, errorMessage = errorMessage)
}


data class ScheduleReminderFiredVO(
    val title: String,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
)

sealed interface AppSideEffects : SideEffect {
    data object ComeFromStandby : AppSideEffects
    data object NoSessionActive : AppSideEffects
    data object OpenSettings : AppSideEffects
    data object RestartApp : AppSideEffects
}