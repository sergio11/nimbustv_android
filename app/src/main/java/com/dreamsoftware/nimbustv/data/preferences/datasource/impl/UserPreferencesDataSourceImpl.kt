package com.dreamsoftware.nimbustv.data.preferences.datasource.impl

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.dreamsoftware.nimbustv.data.preferences.dto.UserPreferencesDTO
import com.dreamsoftware.nimbustv.data.preferences.exception.FetchUserPreferencesLocalException
import com.dreamsoftware.nimbustv.data.preferences.exception.SaveUserPreferencesLocalException
import com.squareup.moshi.Moshi
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import com.dreamsoftware.nimbustv.data.preferences.datasource.IUserPreferencesDataSource

internal class UserPreferencesDataSourceImpl(
    private val dataStore: DataStore<Preferences>,
    private val moshi: Moshi
) : IUserPreferencesDataSource {

    private companion object {
        const val USER_PREFERENCES_KEY = "USER_PREFERENCES_KEY"
    }

    private val userPreferencesPreferenceAdapter by lazy {
        moshi.adapter(UserPreferencesDTO::class.java)
    }

    @Throws(SaveUserPreferencesLocalException::class)
    override suspend fun save(data: UserPreferencesDTO) {
        try {
            val dataStoreKey = stringPreferencesKey(USER_PREFERENCES_KEY)
            dataStore.edit { pref ->
                pref[dataStoreKey] = userPreferencesPreferenceAdapter.toJson(data)
            }
        } catch (ex: Exception) {
            throw SaveUserPreferencesLocalException("An error occurred when trying to save user preferences")
        }
    }

    @Throws(FetchUserPreferencesLocalException::class)
    override suspend fun get(): UserPreferencesDTO {
        try {
            val dataStoreKey = stringPreferencesKey(USER_PREFERENCES_KEY)
            return dataStore.data
                .map { pref -> pref[dataStoreKey]?.let(userPreferencesPreferenceAdapter::fromJson) }
                .firstOrNull() ?: UserPreferencesDTO()
        } catch (ex: Exception) {
            throw FetchUserPreferencesLocalException("An error occurred when trying to fetch user preferences")
        }
    }
}