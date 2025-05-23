package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class SearchChannelsUseCase(
    private val profileRepository: IProfilesRepository,
    private val channelRepository: IChannelRepository
) : FudgeTvUseCaseWithParams<SearchChannelsUseCase.Params, List<ChannelBO>>() {

    override suspend fun onExecuted(params: Params): List<ChannelBO> {
        val profileSelected = profileRepository.getProfileSelected()
        return channelRepository.findAllByProfileIdAndTerm(
            profileId = profileSelected.id,
            term = params.term
        )
    }

    data class Params(
        val term: String
    )
}