package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.PlayListDao
import com.dreamsoftware.nimbustv.data.database.datasource.IPlayListLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.PlayListM3UEntity
import kotlinx.coroutines.CoroutineDispatcher

internal class PlaylistLocalDataSourceImpl(
    playlistDao: PlayListDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<PlayListDao, PlayListM3UEntity>(playlistDao, dispatcher), IPlayListLocalDataSource