package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.DeleteEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgChannelsDataException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.CreateEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.UpdateEpgDataException
import com.dreamsoftware.nimbustv.domain.model.CreateEpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.UpdateEpgBO

interface IEpgRepository {

    @Throws(CreateEpgDataException::class)
    suspend fun create(data: CreateEpgBO): EpgBO

    @Throws(UpdateEpgDataException::class)
    suspend fun update(data: UpdateEpgBO): EpgBO

    @Throws(GetEpgDataException::class)
    suspend fun findById(epgId: String): EpgBO

    @Throws(GetEpgDataException::class)
    suspend fun findAllByProfileId(profileId: String): List<EpgBO>

    @Throws(GetEpgChannelsDataException::class)
    suspend fun findAllChannelsByEpgId(epgId: String): List<EpgChannelBO>

    @Throws(DeleteEpgDataException::class)
    suspend fun deleteAllByEpgId(epgId: String)

    @Throws(DeleteEpgDataException::class)
    suspend fun deleteAllByProfileId(profileId: String)
}