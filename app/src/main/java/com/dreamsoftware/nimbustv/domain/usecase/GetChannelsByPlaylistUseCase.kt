package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import kotlinx.coroutines.delay

class GetChannelsByPlaylistUseCase(
    private val channelRepository: IChannelRepository
) : FudgeTvUseCaseWithParams<GetChannelsByPlaylistUseCase.Params, List<ChannelBO>>() {

    private companion object {
        const val LOAD_CHANNELS_DELAY = 300L
    }

    override suspend fun onExecuted(params: Params): List<ChannelBO> = with(params) {
        delay(LOAD_CHANNELS_DELAY)
        with(channelRepository) {
            if(category != null) {
                findAllByPlaylistIdAndCategory(
                    playlistId = playlistId,
                    category = category
                )
            } else {
                findAllByPlaylistId(playlistId)
            }
        }
    }

    data class Params(
        val playlistId: String,
        val category: String? = null
    )
}