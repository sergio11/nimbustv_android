package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.DeleteEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.SaveEpgDataException
import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO

interface IEpgRepository {

    @Throws(SaveEpgDataException::class)
    suspend fun save(data: List<ChannelEpgDataBO>): List<ChannelEpgDataBO>

    @Throws(GetEpgDataException::class)
    suspend fun findAllByProfileId(profileId: String): List<ChannelEpgDataBO>

    @Throws(DeleteEpgDataException::class)
    suspend fun deleteAllByProfileId(profileId: String)
}