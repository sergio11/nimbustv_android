package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ProfileDao
import com.dreamsoftware.nimbustv.data.database.datasource.IProfileLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import kotlinx.coroutines.CoroutineDispatcher

internal class ProfileLocalDataSourceImpl(
    profileDao: ProfileDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<ProfileDao, ProfileEntity>(profileDao, dispatcher), IProfileLocalDataSource