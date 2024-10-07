package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository

class GetChannelsByPlaylistUseCase(
    private val channelRepository: IChannelRepository
) : FudgeTvUseCaseWithParams<GetChannelsByPlaylistUseCase.Params, List<ChannelBO>>() {

    override suspend fun onExecuted(params: Params): List<ChannelBO> =
        channelRepository.findAllByPlaylistId(params.playlistId)

    data class Params(
        val playlistId: String
    )
}