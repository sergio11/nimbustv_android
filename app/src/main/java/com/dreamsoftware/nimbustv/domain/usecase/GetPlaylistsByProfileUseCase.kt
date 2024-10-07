package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.domain.repository.IPlaylistRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository

class GetPlaylistsByProfileUseCase(
    private val profilesRepository: IProfilesRepository,
    private val playlistRepository: IPlaylistRepository,
) : FudgeTvUseCase<List<PlayListBO>>() {

    override suspend fun onExecuted(): List<PlayListBO> =
        profilesRepository.getProfileSelected()
            .let { profileSelected -> playlistRepository.findAllByProfileId(profileSelected.id) }
}