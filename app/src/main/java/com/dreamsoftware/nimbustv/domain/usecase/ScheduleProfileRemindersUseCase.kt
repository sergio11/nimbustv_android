package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IRemindersRepository
import com.dreamsoftware.nimbustv.domain.service.IReminderSchedulerService

class ScheduleProfileRemindersUseCase(
    private val reminderRepository: IRemindersRepository,
    private val reminderSchedulerService: IReminderSchedulerService
) : FudgeTvUseCaseWithParams<ScheduleProfileRemindersUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) = with(params) {
        reminderRepository.findAllByProfileId(profileId).forEach { reminder ->
            reminderSchedulerService.scheduleReminder(reminder)
        }
    }

    data class Params(
        val profileId: String
    )
}