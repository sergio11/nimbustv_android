package com.dreamsoftware.nimbustv.data.preferences.datasource

import com.dreamsoftware.nimbustv.data.preferences.exception.FetchProfileSelectedPreferenceLocalException
import com.dreamsoftware.nimbustv.data.preferences.exception.SaveProfileSelectedPreferenceLocalException
import kotlin.jvm.Throws

/**
 * An interface representing a data source for profile session-related operations.
 */
interface IProfileSessionDataSource {

    @Throws(SaveProfileSelectedPreferenceLocalException::class)
    suspend fun saveProfileSelectedId(id: String)

    @Throws(SaveProfileSelectedPreferenceLocalException::class)
    suspend fun clearProfileSelectedId()

    @Throws(FetchProfileSelectedPreferenceLocalException::class)
    suspend fun getProfileSelectedId(): String
}