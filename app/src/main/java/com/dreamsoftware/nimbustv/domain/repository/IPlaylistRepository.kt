package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.RepositoryOperationException
import com.dreamsoftware.nimbustv.domain.model.PlayListBO

interface IPlaylistRepository {

    @Throws(RepositoryOperationException::class)
    suspend fun save(account: List<PlayListBO>): List<PlayListBO>
}