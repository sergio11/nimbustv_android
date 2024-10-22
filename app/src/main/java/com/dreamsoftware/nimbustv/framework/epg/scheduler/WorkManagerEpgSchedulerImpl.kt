package com.dreamsoftware.nimbustv.framework.epg.scheduler

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService
import com.dreamsoftware.nimbustv.framework.epg.scheduler.worker.SyncEpgDataWorker
import java.util.Calendar
import java.util.UUID
import java.util.concurrent.TimeUnit

/**
 * WorkManagerEpgSchedulerImpl is an implementation of the IEpgSchedulerService
 * that handles scheduling and canceling of the periodic background work for
 * synchronizing EPG (Electronic Program Guide) data using WorkManager.
 *
 * This class schedules a periodic task to sync EPG data daily at the start
 * of each day (midnight) for a given profile, ensuring it only runs when the
 * device is connected to the network.
 *
 * @param workManager The instance of WorkManager used to schedule and manage
 * the background work.
 */
internal class WorkManagerEpgSchedulerImpl(
    private val workManager: WorkManager
) : IEpgSchedulerService {

    private companion object {
        const val REPEAT_INTERVAL_IN_DAYS = 1L
    }

    /**
     * Schedules a periodic work request to sync EPG data for a specific profile.
     * The work is scheduled to run daily at the start of the day (midnight).
     *
     * Constraints are applied to ensure the work only runs when the device
     * is connected to a network.
     *
     * @param url The URL to fetch the EPG data from.
     * @param profileId The unique identifier for the profile to sync.
     */
    override fun scheduleSyncEpgWorkForProfile(url: String, profileId: String) {
        // Define constraints to ensure network connectivity
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        // Calculate the delay to start the work at the beginning of the next day
        val initialDelay = calculateInitialDelayForNextDay()

        // Create a PeriodicWorkRequest to run daily
        val workRequest =
            PeriodicWorkRequestBuilder<SyncEpgDataWorker>(REPEAT_INTERVAL_IN_DAYS, TimeUnit.DAYS)
                .setInputData(SyncEpgDataWorker.buildInputData(url, profileId))
                .setConstraints(constraints)
                .setId(UUID.fromString(profileId))
                .setInitialDelay(initialDelay, TimeUnit.MILLISECONDS)
                .build()

        // Schedule the work uniquely based on the worker's name and the profile ID
        workManager.enqueueUniquePeriodicWork(
            SyncEpgDataWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    /**
     * Cancels any scheduled EPG sync work for the specified profile.
     *
     * @param profileId The unique identifier for the profile whose work is to be canceled.
     */
    override fun cancelSyncEpgWork(profileId: String) {
        // Cancel the scheduled work by the profile ID
        workManager.cancelWorkById(UUID.fromString(profileId))
    }

    /**
     * Calculates the initial delay needed to start the periodic work at the
     * beginning of the next day (midnight). This is done by calculating the
     * difference between the current time and the next midnight.
     *
     * @return The delay in milliseconds until the start of the next day.
     */
    private fun calculateInitialDelayForNextDay(): Long {
        // Get the current time
        val currentTime = Calendar.getInstance()

        // Set the next run time to midnight of the next day
        val nextRunTime = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, 1)
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        // Return the difference in time between now and midnight of the next day
        return nextRunTime.timeInMillis - currentTime.timeInMillis
    }
}