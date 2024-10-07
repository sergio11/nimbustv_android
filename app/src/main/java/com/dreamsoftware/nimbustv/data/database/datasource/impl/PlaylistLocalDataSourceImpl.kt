package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.PlayListDao
import com.dreamsoftware.nimbustv.data.database.datasource.IPlayListLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import kotlinx.coroutines.CoroutineDispatcher

internal class PlaylistLocalDataSourceImpl(
    playlistDao: PlayListDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<PlayListDao, PlayListEntity, String>(playlistDao, dispatcher), IPlayListLocalDataSource