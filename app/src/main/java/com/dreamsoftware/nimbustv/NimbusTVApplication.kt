package com.dreamsoftware.nimbustv

import android.app.Application
import androidx.hilt.work.HiltWorkerFactory
import androidx.work.Configuration
import com.dreamsoftware.fudge.utils.IFudgeAppEvent
import com.dreamsoftware.fudge.utils.IFudgeTvApplicationAware
import dagger.hilt.android.HiltAndroidApp
import java.time.LocalDateTime
import javax.inject.Inject

@HiltAndroidApp
class NimbusTVApplication : Application(), IFudgeTvApplicationAware, Configuration.Provider {
    @Inject
    lateinit var hiltWorkerFactory: HiltWorkerFactory

    override val workManagerConfiguration: Configuration
        get() = Configuration.Builder()
            .setWorkerFactory(hiltWorkerFactory)
            .build()
}


sealed interface AppEvent : IFudgeAppEvent {
    data object GoToStandby : AppEvent
    data object ComeFromStandby : AppEvent
    data object SignOff : AppEvent
    data class NetworkConnectivityStateChanged(val lastState: Boolean, val newState: Boolean) :
        AppEvent
    data object UserPreferencesUpdated: AppEvent
    data class ScheduleReminderFired(
        val scheduleId: String,
        val title: String,
        val description: String?,
        val startTime: LocalDateTime,
        val endTime: LocalDateTime,
    ): AppEvent
}

