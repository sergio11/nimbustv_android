package com.dreamsoftware.nimbustv.data.preferences.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dreamsoftware.nimbustv.data.preferences.datasource.IProfileSessionDataSource
import com.dreamsoftware.nimbustv.data.preferences.exception.FetchProfileSelectedPreferenceLocalException
import com.dreamsoftware.nimbustv.data.preferences.exception.SaveProfileSelectedPreferenceLocalException
import com.dreamsoftware.nimbustv.ui.utils.EMPTY
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map

/**
 * Implementation of [IProfileSessionDataSource] for managing profile session-related data using DataStore.
 * @param dataStore The DataStore instance used for storing preferences.
 */
internal class ProfileSessionDataSourceImpl(
    private val dataStore: DataStore<Preferences>
) : IProfileSessionDataSource {

    private companion object {
        const val PROFILE_SELECTED_KEY = "PROFILE_SELECTED_KEY"
    }

    @Throws(SaveProfileSelectedPreferenceLocalException::class)
    override suspend fun saveProfileSelectedId(id: String) {
        val dataStoreKey = stringPreferencesKey(PROFILE_SELECTED_KEY)
        dataStore.edit { pref -> pref[dataStoreKey] = id }
    }

    @Throws(SaveProfileSelectedPreferenceLocalException::class)
    override suspend fun clearProfileSelectedId() {
        val dataStoreKey = stringPreferencesKey(PROFILE_SELECTED_KEY)
        dataStore.edit { pref -> pref[dataStoreKey] = String.EMPTY }
    }

    @Throws(FetchProfileSelectedPreferenceLocalException::class)
    override suspend fun getProfileSelectedId(): String {
        val dataStoreKey = stringPreferencesKey(PROFILE_SELECTED_KEY)
        return dataStore.data
            .map { pref -> pref[dataStoreKey] }
            .firstOrNull() ?: throw FetchProfileSelectedPreferenceLocalException("profile not found")
    }
}