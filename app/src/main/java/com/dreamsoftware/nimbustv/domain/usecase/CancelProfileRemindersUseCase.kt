package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IRemindersRepository
import com.dreamsoftware.nimbustv.domain.service.IReminderSchedulerService

class CancelProfileRemindersUseCase(
    private val reminderRepository: IRemindersRepository,
    private val reminderSchedulerService: IReminderSchedulerService
) : FudgeTvUseCaseWithParams<CancelProfileRemindersUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) = with(params) {
        reminderRepository.findAllByProfileId(profileId).forEach {
            reminderSchedulerService.cancelReminder(it.id)
        }
    }

    data class Params(
        val profileId: String
    )
}