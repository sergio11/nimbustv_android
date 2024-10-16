package com.dreamsoftware.nimbustv.domain.service

import com.dreamsoftware.nimbustv.domain.exception.ParseEpgFailedException
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO

interface IEpgParserService {

    @Throws(ParseEpgFailedException::class)
    suspend fun parseEpgData(url: String): List<EpgDataBO>
}