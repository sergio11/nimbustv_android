package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.FavoriteChannelDao
import com.dreamsoftware.nimbustv.data.database.datasource.IFavoriteChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.entity.FavoriteChannelEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import kotlinx.coroutines.CoroutineDispatcher

internal class FavoriteChannelLocalDataSourceImpl(
    private val favoriteChannelDao: FavoriteChannelDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<FavoriteChannelDao, FavoriteChannelEntity, Long>(favoriteChannelDao, dispatcher),
    IFavoriteChannelLocalDataSource {

    @Throws(AccessDatabaseException::class)
    override suspend fun findFavoriteChannelsByProfileId(profileId: String): List<ChannelEntity> = safeExecute {
        favoriteChannelDao.findFavoriteChannelsByProfileId(profileId)
    }
}