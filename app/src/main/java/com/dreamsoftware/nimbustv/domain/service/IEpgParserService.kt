package com.dreamsoftware.nimbustv.domain.service

import com.dreamsoftware.nimbustv.domain.exception.ParseEpgFailedException
import com.dreamsoftware.nimbustv.domain.model.ChannelEpgDataBO

interface IEpgParserService {

    @Throws(ParseEpgFailedException::class)
    suspend fun parseEpgData(profileId: String, url: String): List<ChannelEpgDataBO>
}