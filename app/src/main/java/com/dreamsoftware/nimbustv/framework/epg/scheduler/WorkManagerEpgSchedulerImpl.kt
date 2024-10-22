package com.dreamsoftware.nimbustv.framework.epg.scheduler

import androidx.work.Constraints
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequestBuilder
import androidx.work.WorkManager
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService
import com.dreamsoftware.nimbustv.framework.epg.scheduler.worker.SaveEpgWorker
import java.util.UUID
import java.util.concurrent.TimeUnit

internal class WorkManagerEpgSchedulerImpl(
    private val workManager: WorkManager
) : IEpgSchedulerService {

    private companion object {
        const val WORK_INTERVAL_MINUTES = 15L
    }

    override fun scheduleSyncEpgWorkForProfile(url: String, profileId: String) {
        val constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()
        val workRequest =
            PeriodicWorkRequestBuilder<SaveEpgWorker>(WORK_INTERVAL_MINUTES, TimeUnit.MINUTES)
                .setInputData(SaveEpgWorker.buildInputData(url, profileId))
                .setConstraints(constraints)
                .setId(UUID.fromString(profileId))
                .setInitialDelay(WORK_INTERVAL_MINUTES, TimeUnit.MINUTES)
                .build()
        // Enqueue the work request, using a unique name for the work
        workManager.enqueueUniquePeriodicWork(
            SaveEpgWorker.WORK_NAME,
            ExistingPeriodicWorkPolicy.UPDATE,
            workRequest
        )
    }

    override fun cancelSyncEpgWork(profileId: String) {
        workManager.cancelWorkById(UUID.fromString(profileId))
    }
}