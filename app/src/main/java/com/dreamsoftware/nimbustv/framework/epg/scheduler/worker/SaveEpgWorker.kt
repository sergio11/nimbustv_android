package com.dreamsoftware.nimbustv.framework.epg.scheduler.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgParserService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject


@HiltWorker
class SaveEpgWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val epgRepository: IEpgRepository,
    private val epgParserService: IEpgParserService
) : CoroutineWorker(context, workerParams) {

    companion object {

        const val WORK_NAME = "SaveEpgWork"
        private const val KEY_EPG_URL = "epg_url"
        private const val KEY_PROFILE_ID = "profile_id"

        @JvmStatic
        fun buildInputData(epgUrl: String, profileId: String) =
            Data.Builder()
                .putString(KEY_EPG_URL, epgUrl)
                .putString(KEY_PROFILE_ID, profileId)
                .build()
    }

    override suspend fun doWork(): Result {
        return try {
            val url = inputData.getString(KEY_EPG_URL)
            val profileId = inputData.getString(KEY_PROFILE_ID)
            Log.d("ATV_WORKER", "SaveEpgWorker url: $url, profileId: $profileId")

            Result.success()
        } catch (exception: Exception) {
            Result.failure()
        }
    }
}