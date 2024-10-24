package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.GetUserPreferencesException
import com.dreamsoftware.nimbustv.domain.exception.SaveUserPreferencesException
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO

interface IPreferencesRepository {

    @Throws(SaveUserPreferencesException::class)
    suspend fun saveUserPreferences(data: UserPreferenceBO)

    @Throws(GetUserPreferencesException::class)
    suspend fun getUserPreferences(): UserPreferenceBO
}