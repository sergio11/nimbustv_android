package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ChannelDao
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import kotlinx.coroutines.CoroutineDispatcher

internal class ChannelLocalDataSourceImpl(
    channelDao: ChannelDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<ChannelDao, ChannelEntity>(channelDao, dispatcher), IChannelLocalDataSource