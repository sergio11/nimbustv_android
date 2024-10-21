package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository

class DeleteChannelByIdUseCase(
    private val channelRepository: IChannelRepository
) : FudgeTvUseCaseWithParams<DeleteChannelByIdUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        channelRepository.deleteById(params.channelId)
    }

    data class Params(
        val channelId: String
    )
}