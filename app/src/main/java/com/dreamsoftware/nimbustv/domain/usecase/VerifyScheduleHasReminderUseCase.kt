package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IRemindersRepository

class VerifyScheduleHasReminderUseCase(
    private val remindersRepository: IRemindersRepository
): FudgeTvUseCaseWithParams<VerifyScheduleHasReminderUseCase.Params, Boolean>() {

    override suspend fun onExecuted(params: Params): Boolean =
        remindersRepository.hasReminder(params.scheduleId)

    data class Params(
        val scheduleId: String
    )
}