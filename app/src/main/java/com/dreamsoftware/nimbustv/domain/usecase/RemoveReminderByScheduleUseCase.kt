package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IRemindersRepository
import com.dreamsoftware.nimbustv.domain.service.IReminderSchedulerService

class RemoveReminderByScheduleUseCase(
    private val remindersRepository: IRemindersRepository,
    private val reminderSchedulerService: IReminderSchedulerService
) : FudgeTvUseCaseWithParams<RemoveReminderByScheduleUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params): Unit = with(params) {
        with(remindersRepository) {
            val reminder = findByScheduleId(scheduleId)
            deleteById(reminder.id).also {
                reminderSchedulerService.cancelReminder(reminder.id)
            }
        }

    }

    data class Params(
        val scheduleId: String
    )
}