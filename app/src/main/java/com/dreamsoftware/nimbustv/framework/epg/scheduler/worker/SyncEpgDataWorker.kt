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
class SyncEpgDataWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
    private val epgRepository: IEpgRepository,
    private val epgParserService: IEpgParserService
) : CoroutineWorker(context, workerParams) {

    companion object {

        const val WORK_NAME = "SyncEpgDataWorker"
        private const val KEY_EPG_URL = "epg_url"
        private const val KEY_PROFILE_ID = "profile_id"

        @JvmStatic
        fun buildInputData(epgUrl: String, profileId: String) =
            Data.Builder()
                .putString(KEY_EPG_URL, epgUrl)
                .putString(KEY_PROFILE_ID, profileId)
                .build()
    }

    override suspend fun doWork(): Result = try {
        val url = inputData.getString(KEY_EPG_URL).orEmpty()
        val profileId = inputData.getString(KEY_PROFILE_ID).orEmpty()
        if(url.isNotEmpty() && profileId.isNotEmpty()) {
            Log.d(WORK_NAME, "SaveEpgWorker url: $url, profileId: $profileId")
            val data = epgParserService.parseEpgData(
                profileId = profileId,
                url = url
            )
            with(epgRepository) {
                deleteAllByProfileId(profileId)
                epgRepository.save(data)
            }
            Result.success()
        } else {
            Result.failure()
        }
    } catch (exception: Exception) {
        Log.e(WORK_NAME, "An error occurred with trying to sync epg data: ${exception.message}")
        Result.failure()
    }
}