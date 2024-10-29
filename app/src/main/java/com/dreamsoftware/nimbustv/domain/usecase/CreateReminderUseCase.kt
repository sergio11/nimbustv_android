package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.CreateReminderBO
import com.dreamsoftware.nimbustv.domain.model.ReminderBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.repository.IRemindersRepository
import com.dreamsoftware.nimbustv.domain.service.IReminderSchedulerService
import java.util.UUID

class CreateReminderUseCase(
    private val profileRepository: IProfilesRepository,
    private val remindersRepository: IRemindersRepository,
    private val reminderSchedulerService: IReminderSchedulerService
) : FudgeTvUseCaseWithParams<CreateReminderUseCase.Params, ReminderBO>() {

    override suspend fun onExecuted(params: Params): ReminderBO = with(params) {
        val profileSelected = profileRepository.getProfileSelected()
        val reminderId = UUID.randomUUID().toString()
        remindersRepository.create(
            CreateReminderBO(
                id = reminderId,
                scheduleId = scheduleId,
                profileId = profileSelected.id
            )
        ).also {
            reminderSchedulerService.scheduleReminder(reminderId)
        }
    }

    data class Params(
        val scheduleId: String
    )
}