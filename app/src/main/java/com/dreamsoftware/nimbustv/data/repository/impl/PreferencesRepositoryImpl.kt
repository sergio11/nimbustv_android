package com.dreamsoftware.nimbustv.data.repository.impl

import com.dreamsoftware.nimbustv.data.preferences.datasource.IUserPreferencesDataSource
import com.dreamsoftware.nimbustv.data.preferences.dto.UserPreferencesDTO
import com.dreamsoftware.nimbustv.data.preferences.exception.FetchUserPreferencesLocalException
import com.dreamsoftware.nimbustv.data.preferences.exception.SaveUserPreferencesLocalException
import com.dreamsoftware.nimbustv.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.nimbustv.domain.exception.GetUserPreferencesException
import com.dreamsoftware.nimbustv.domain.exception.SaveUserPreferencesException
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO
import com.dreamsoftware.nimbustv.domain.repository.IPreferencesRepository
import com.dreamsoftware.nimbustv.utils.IMapper
import kotlinx.coroutines.CoroutineDispatcher

internal class PreferencesRepositoryImpl(
    private val userPreferencesDataSource: IUserPreferencesDataSource,
    private val userPreferencesMapper: IMapper<UserPreferencesDTO, UserPreferenceBO>,
    dispatcher: CoroutineDispatcher
): SupportRepositoryImpl(dispatcher), IPreferencesRepository {

    @Throws(SaveUserPreferencesException::class)
    override suspend fun saveUserPreferences(data: UserPreferenceBO): Unit = safeExecute {
        try {
            userPreferencesDataSource.save(userPreferencesMapper.mapOutToIn(data))
        } catch (ex: SaveUserPreferencesLocalException) {
            throw SaveUserPreferencesException("An error occurred when trying to save user preferences", ex)
        }
    }

    @Throws(GetUserPreferencesException::class)
    override suspend fun getUserPreferences(): UserPreferenceBO = safeExecute {
        try {
            userPreferencesDataSource.get()
                .let(userPreferencesMapper::mapInToOut)
        } catch (ex: FetchUserPreferencesLocalException) {
            throw GetUserPreferencesException("An error occurred when trying to fetch user preferences", ex)
        }
    }
}