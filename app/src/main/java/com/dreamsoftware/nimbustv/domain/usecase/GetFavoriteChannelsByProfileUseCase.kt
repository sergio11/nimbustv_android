package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class GetFavoriteChannelsByProfileUseCase(
    private val profileRepository: IProfilesRepository,
    private val channelRepository: IChannelRepository
) : FudgeTvUseCase<List<ChannelBO>>() {

    override suspend fun onExecuted(): List<ChannelBO> {
        val profileSelected = profileRepository.getProfileSelected()
        return channelRepository.findAllFavoriteChannelsByProfileId(profileId = profileSelected.id)
    }
}