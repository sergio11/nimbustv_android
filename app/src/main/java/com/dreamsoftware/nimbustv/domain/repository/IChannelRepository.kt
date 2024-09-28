package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.RepositoryOperationException
import com.dreamsoftware.nimbustv.domain.model.ChannelBO

interface IChannelRepository {

    @Throws(RepositoryOperationException::class)
    suspend fun save(account: List<ChannelBO>): List<ChannelBO>
}