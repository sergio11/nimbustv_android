package com.dreamsoftware.nimbustv.data.preferences.datasource

import com.dreamsoftware.nimbustv.data.preferences.dto.UserPreferencesDTO
import com.dreamsoftware.nimbustv.data.preferences.exception.FetchUserPreferencesLocalException
import com.dreamsoftware.nimbustv.data.preferences.exception.SaveUserPreferencesLocalException

interface IUserPreferencesDataSource {

    @Throws(SaveUserPreferencesLocalException::class)
    suspend fun save(data: UserPreferencesDTO)

    @Throws(FetchUserPreferencesLocalException::class)
    suspend fun get(): UserPreferencesDTO
}