package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class GetEpgDataUseCase(
    private val profileRepository: IProfilesRepository,
    private val epgRepository: IEpgRepository
) : FudgeTvUseCase<List<ChannelEpgDataBO>>() {

    override suspend fun onExecuted(): List<ChannelEpgDataBO> {
        val profileSelected = profileRepository.getProfileSelected()
        return epgRepository.findAllByProfileId(profileSelected.id)
    }
}