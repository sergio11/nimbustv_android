package com.dreamsoftware.nimbustv.data.database.datasource

import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.ISupportLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.entity.FavoriteChannelEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException

interface IFavoriteChannelLocalDataSource: ISupportLocalDataSource<FavoriteChannelEntity, Long> {

    @Throws(AccessDatabaseException::class)
    suspend fun findFavoriteChannelsByProfileId(profileId: String): List<ChannelEntity>

    @Throws(AccessDatabaseException::class)
    suspend fun deleteByChannelIdAndProfileId(channelId: String, profileId: String)

    @Throws(AccessDatabaseException::class)
    suspend fun isFavoriteChannel(channelId: String, profileId: String): Boolean
}