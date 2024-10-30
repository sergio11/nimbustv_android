import androidx.work.ExistingWorkPolicy
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import com.dreamsoftware.nimbustv.domain.model.ReminderBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.service.IReminderSchedulerService
import com.dreamsoftware.nimbustv.framework.reminder.worker.ReminderWorker
import java.time.Duration
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.concurrent.TimeUnit

internal class ReminderSchedulerWorkManagerImpl(
    private val workManager: WorkManager,
    private val epgRepository: IEpgRepository
) : IReminderSchedulerService {

    companion object {
        private const val REMINDER_OFFSET_MINUTES = 5L
    }

    override suspend fun scheduleReminder(reminder: ReminderBO) {
        val schedule = epgRepository.findScheduleById(reminder.scheduleId)
        val currentTime = LocalDateTime.now().toInstant(ZoneOffset.UTC)
        val reminderTime = schedule.startTime.minusMinutes(REMINDER_OFFSET_MINUTES)
        val delayDuration = Duration.between(currentTime, reminderTime.toInstant(ZoneOffset.UTC))
        val delayInMillis = delayDuration.toMillis().coerceAtLeast(0)

        val workRequest = OneTimeWorkRequestBuilder<ReminderWorker>()
            .setInitialDelay(delayInMillis, TimeUnit.MILLISECONDS)
            .setInputData(ReminderWorker.buildInputData(reminder.id))
            .build()

        workManager.enqueueUniqueWork(
            reminder.id,
            ExistingWorkPolicy.REPLACE,
            workRequest
        )
    }

    override suspend fun cancelReminder(reminderId: String) {
        workManager.cancelUniqueWork(reminderId)
    }
}