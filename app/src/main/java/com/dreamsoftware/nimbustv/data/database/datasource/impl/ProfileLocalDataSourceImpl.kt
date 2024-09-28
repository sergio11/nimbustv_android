package com.dreamsoftware.nimbustv.data.database.datasource.impl

import com.dreamsoftware.nimbustv.data.database.dao.ProfileDao
import com.dreamsoftware.nimbustv.data.database.datasource.IProfileLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.SupportLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.data.database.exception.RecordNotFoundException
import kotlinx.coroutines.CoroutineDispatcher

internal class ProfileLocalDataSourceImpl(
    private val profileDao: ProfileDao,
    dispatcher: CoroutineDispatcher
): SupportLocalDataSourceImpl<ProfileDao, ProfileEntity>(profileDao, dispatcher), IProfileLocalDataSource {

    override suspend fun verifyPin(profileId: Long, securedPin: Int): Boolean = safeExecute {
        with(profileDao) {
            val profile = getById(profileId) ?: throw RecordNotFoundException("record not found")
            profile.securedPin == securedPin.toString()
        }
    }
}