package com.dreamsoftware.nimbustv.domain.usecase

import android.util.Log
import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class AddFavoriteChannelUseCase(
    private val profileRepository: IProfilesRepository,
    private val channelRepository: IChannelRepository
) : FudgeTvUseCaseWithParams<AddFavoriteChannelUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        val profileSelected = profileRepository.getProfileSelected()
        Log.d("ATV_FAVORITES", "AddFavoriteChannelUseCase params.channelId: ${params.channelId} - profileId: ${profileSelected.id}")
        channelRepository.addToFavorites(
            channelId = params.channelId,
            profileId = profileSelected.id
        )
    }

    data class Params(
        val channelId: String
    )
}