package com.dreamsoftware.nimbustv.data.database.datasource

import com.dreamsoftware.nimbustv.data.database.datasource.impl.core.ISupportLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.data.database.exception.AccessDatabaseException
import com.dreamsoftware.nimbustv.data.database.exception.RecordNotFoundException

interface IProfileLocalDataSource: ISupportLocalDataSource<ProfileEntity, String> {

    @Throws(RecordNotFoundException::class, AccessDatabaseException::class)
    suspend fun verifyPin(profileId: String, securedPin: Int): Boolean
}