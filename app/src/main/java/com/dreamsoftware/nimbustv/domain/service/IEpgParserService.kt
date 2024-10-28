package com.dreamsoftware.nimbustv.domain.service

import com.dreamsoftware.nimbustv.domain.exception.ParseEpgFailedException
import com.dreamsoftware.nimbustv.domain.model.EpgChannelEntryBO

interface IEpgParserService {

    @Throws(ParseEpgFailedException::class)
    suspend fun parseEpgData(url: String): List<EpgChannelEntryBO>
}