package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.repository.IPlaylistRepository

class DeletePlaylistUseCase(
    private val playlistRepository: IPlaylistRepository
) : FudgeTvUseCaseWithParams<DeletePlaylistUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        playlistRepository.deleteById(params.playlistId)
    }

    data class Params(
        val playlistId: String
    )
}