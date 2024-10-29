package com.dreamsoftware.nimbustv.framework.reminder.worker

import android.content.Context
import android.util.Log
import androidx.hilt.work.HiltWorker
import androidx.work.CoroutineWorker
import androidx.work.Data
import androidx.work.WorkerParameters
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@HiltWorker
class ReminderWorker @AssistedInject constructor(
    @Assisted context: Context,
    @Assisted workerParams: WorkerParameters,
) : CoroutineWorker(context, workerParams) {

    companion object {

        const val WORK_NAME = "ReminderWorker"
        private const val KEY_REMINDER_ID = "reminder_id"

        @JvmStatic
        fun buildInputData(reminderId: String) =
            Data.Builder()
                .putString(KEY_REMINDER_ID, reminderId)
                .build()
    }

    override suspend fun doWork(): Result = try {
        val reminderId = inputData.getString(KEY_REMINDER_ID).orEmpty()
        if(reminderId.isNotEmpty()) {
            Log.d(WORK_NAME, "ReminderWorker reminderId: $reminderId")
            Result.success()
        } else {
            Result.failure()
        }
    } catch (exception: Exception) {
        Log.e(WORK_NAME, "An error occurred with trying to launch reminder: ${exception.message}")
        Result.failure()
    }
}