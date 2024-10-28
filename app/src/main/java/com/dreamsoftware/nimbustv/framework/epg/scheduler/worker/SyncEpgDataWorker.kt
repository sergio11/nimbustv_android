package com.dreamsoftware.nimbustv.framework.epg.scheduler.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import com.dreamsoftware.nimbustv.domain.extensions.mapToCreateEpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.UpdateEpgBO
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
        private const val KEY_EPG_ID = "epg_id"

        @JvmStatic
        fun buildInputData(epgId: String) =
            Data.Builder()
                .putString(KEY_EPG_ID, epgId)
                .build()
    }

    override suspend fun doWork(): Result = try {
        val epgId = inputData.getString(KEY_EPG_ID).orEmpty()
        if(epgId.isNotEmpty()) {
            Log.d(WORK_NAME, "SaveEpgWorker epgId: $epgId")
            with(epgRepository) {
                val epg = findById(epgId)
                val data = epgParserService.parseEpgData(url = epg.url)
                update(UpdateEpgBO(
                    id = epgId,
                    channelList = data.mapToCreateEpgChannelBO(epgId)
                ))
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