package com.dreamsoftware.nimbustv.domain.service

import com.dreamsoftware.nimbustv.domain.exception.ParseEpgFailedException
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO

interface IEpgParserService {

    @Throws(ParseEpgFailedException::class)
    suspend fun parseEpgData(profileId: String, url: String): List<EpgDataBO>
}