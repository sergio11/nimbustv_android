package com.dreamsoftware.nimbustv.data.database.datasource

import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.ISupportLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException

interface IChannelLocalDataSource: ISupportLocalDataSource<ChannelEntity, String> {

    @Throws(AccessDatabaseException::class)
    suspend fun findAllByPlaylistId(playlistId: String): List<ChannelEntity>

    @Throws(AccessDatabaseException::class)
    suspend fun findAllByPlaylistIdAndCategory(playlistId: String, category: String): List<ChannelEntity>

    @Throws(AccessDatabaseException::class)
    suspend fun findAllByProfileIdAndTerm(profileId: String, term: String): List<ChannelEntity>
}