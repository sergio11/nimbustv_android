package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.model.CreatePlayListBO
import com.dreamsoftware.nimbustv.domain.model.M3uEntryBO
import com.dreamsoftware.nimbustv.domain.model.SaveChannelBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.domain.repository.IPlaylistRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.service.IPlaylistParserService

class CreatePlaylistUseCase(
    private val profilesRepository: IProfilesRepository,
    private val playlistParserService: IPlaylistParserService,
    private val playlistRepository: IPlaylistRepository,
    private val channelRepository: IChannelRepository
) : FudgeTvUseCaseWithParams<CreatePlaylistUseCase.Params, Unit>() {

    override suspend fun onExecuted(params: Params) {
        with(params) {
            val profileSelected = profilesRepository.getProfileSelected()
            val playlistEntries = playlistParserService.parsePlaylist(url)
            val playlistSaved = playlistRepository.insert(
                CreatePlayListBO(
                    alias = alias,
                    url = url,
                    profileId = profileSelected.id,
                    channelsCount = playlistEntries.size.toLong()
                )
            )
            channelRepository.save(playlistEntries.toSaveChannelBOList(playlistSaved.id))
        }
    }

    private fun List<M3uEntryBO>.toSaveChannelBOList(playlistId: String) =
        map {
            SaveChannelBO(
                title = it.title,
                url = it.url,
                icon = it.icon,
                category = it.category,
                playlistId = playlistId,
                manifestType = it.manifestType,
                licenseType = it.licenseType,
                licenseKey = it.licenseKey,
                streamTypeEnum = it.streamTypeEnum
            )
        }

    data class Params(
        val alias: String,
        val url: String
    )
}