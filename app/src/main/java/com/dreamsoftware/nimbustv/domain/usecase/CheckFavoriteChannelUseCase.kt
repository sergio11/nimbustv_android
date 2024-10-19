package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class CheckFavoriteChannelUseCase(
    private val profileRepository: IProfilesRepository,
    private val channelRepository: IChannelRepository
) : FudgeTvUseCaseWithParams<CheckFavoriteChannelUseCase.Params, Boolean>() {

    override suspend fun onExecuted(params: Params): Boolean {
        val profileSelected = profileRepository.getProfileSelected()
        return channelRepository.isFavoriteChannel(
            channelId = params.channelId,
            profileId = profileSelected.id
        )
    }

    data class Params(
        val channelId: String
    )
}