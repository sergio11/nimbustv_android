package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.GetEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.SaveEpgDataException
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO

interface IEpgRepository {

    @Throws(SaveEpgDataException::class)
    suspend fun save(data: List<EpgDataBO>): List<EpgDataBO>

    @Throws(GetEpgDataException::class)
    suspend fun findAllByProfileId(profileId: String): List<EpgDataBO>
}