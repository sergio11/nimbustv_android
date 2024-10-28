package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.DeleteEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgChannelsDataException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.SaveEpgDataException
import com.dreamsoftware.nimbustv.domain.model.CreateEpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgChannelBO

interface IEpgRepository {

    @Throws(SaveEpgDataException::class)
    suspend fun save(data: CreateEpgBO): EpgBO

    @Throws(GetEpgDataException::class)
    suspend fun findAllByProfileId(profileId: String): List<EpgBO>

    @Throws(GetEpgChannelsDataException::class)
    suspend fun findAllChannelsByEpgId(epgId: String): List<EpgChannelBO>

    @Throws(DeleteEpgDataException::class)
    suspend fun deleteAllByProfileId(profileId: String)
}