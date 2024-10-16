package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ChannelEpgDao
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import kotlinx.coroutines.CoroutineDispatcher

internal class ChannelEpgLocalDataSourceImpl(
    channelEpgDao: ChannelEpgDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<ChannelEpgDao, ChannelEpgEntity, String>(channelEpgDao, dispatcher), IChannelEpgLocalDataSource