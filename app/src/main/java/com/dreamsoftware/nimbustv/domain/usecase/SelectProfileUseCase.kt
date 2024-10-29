package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class SelectProfileUseCase(
    private val profilesRepository: IProfilesRepository,
    private val cancelProfileRemindersUseCase: CancelProfileRemindersUseCase,
    private val scheduleProfileRemindersUseCase: ScheduleProfileRemindersUseCase
) : FudgeTvUseCaseWithParams<SelectProfileUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params): Unit = with(params) {
        val profileSelected = runCatching { profilesRepository.getProfileSelected() }.getOrNull()
        profilesRepository.selectProfile(profile)
        if (profileSelected != null && profileSelected.id != profile.id) {
            cancelProfileRemindersUseCase.onExecuted(CancelProfileRemindersUseCase.Params(profileSelected.id))
        }
        if (profileSelected == null || profileSelected.id != profile.id) {
            scheduleProfileRemindersUseCase.onExecuted(ScheduleProfileRemindersUseCase.Params(profile.id))
        }
    }

    data class Params(
        val profile: ProfileBO
    )
}