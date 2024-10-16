package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ProgrammeDao
import com.dreamsoftware.nimbustv.data.database.datasource.IProgrammeLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ProgrammeEntity
import kotlinx.coroutines.CoroutineDispatcher

internal class ProgrammeLocalDataSourceImpl(
    programmeDao: ProgrammeDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<ProgrammeDao, ProgrammeEntity, String>(programmeDao, dispatcher), IProgrammeLocalDataSource