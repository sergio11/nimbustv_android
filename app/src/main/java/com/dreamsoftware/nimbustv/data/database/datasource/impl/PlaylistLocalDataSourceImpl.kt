package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.PlayListDao
import com.dreamsoftware.nimbustv.data.database.datasource.IPlayListLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import kotlinx.coroutines.CoroutineDispatcher

internal class PlaylistLocalDataSourceImpl(
    private val playlistDao: PlayListDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<PlayListDao, PlayListEntity, String>(playlistDao, dispatcher), IPlayListLocalDataSource {

    @Throws(AccessDatabaseException::class)
    override suspend fun findAllByProfileId(profileId: String): List<PlayListEntity> = safeExecute {
        playlistDao.findAllByProfileId(profileId)
    }
}