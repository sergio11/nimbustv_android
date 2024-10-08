package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository

class GetChannelByIdUseCase(
    private val channelRepository: IChannelRepository
) : FudgeTvUseCaseWithParams<GetChannelByIdUseCase.Params, ChannelBO>() {

    override suspend fun onExecuted(params: Params): ChannelBO =
        channelRepository.findById(params.channelId)

    data class Params(
        val channelId: String
    )
}