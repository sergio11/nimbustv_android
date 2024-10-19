package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class RemoveChannelFromFavoritesUseCase(
    private val profileRepository: IProfilesRepository,
    private val channelRepository: IChannelRepository
) : FudgeTvUseCaseWithParams<RemoveChannelFromFavoritesUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        val profileSelected = profileRepository.getProfileSelected()
        channelRepository.removeFromFavorites(
            channelId = params.channelId,
            profileId = profileSelected.id
        )
    }

    data class Params(
        val channelId: String
    )
}