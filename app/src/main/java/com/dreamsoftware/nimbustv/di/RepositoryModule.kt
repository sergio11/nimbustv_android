package com.dreamsoftware.nimbustv.di

import com.dreamsoftware.nimbustv.data.database.datasource.IProfileLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.data.preferences.datasource.IProfileSessionDataSource
import com.dreamsoftware.nimbustv.data.repository.impl.ProfilesRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.mapper.CreateProfileMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.ProfileMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.UpdateProfileMapper
import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Singleton
    fun provideProfileMapper(): IOneSideMapper<ProfileEntity, ProfileBO> = ProfileMapper()

    @Provides
    @Singleton
    fun provideCreateProfileMapper(): IOneSideMapper<CreateProfileRequestBO, ProfileEntity> =
        CreateProfileMapper()

    @Provides
    @Singleton
    fun provideUpdateProfileMapper(): IOneSideMapper<UpdatedProfileRequestBO, ProfileEntity> =
        UpdateProfileMapper()

    @Provides
    @Singleton
    fun provideProfilesRepository(
        profileLocalDataSource: IProfileLocalDataSource,
        profilesMapper: IOneSideMapper<ProfileEntity, ProfileBO>,
        createProfileMapper: IOneSideMapper<CreateProfileRequestBO, ProfileEntity>,
        updateProfileMapper: IOneSideMapper<UpdatedProfileRequestBO, ProfileEntity>,
        profileSessionDataSource: IProfileSessionDataSource,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IProfilesRepository =
        ProfilesRepositoryImpl(
            profileLocalDataSource,
            profilesMapper,
            createProfileMapper,
            updateProfileMapper,
            profileSessionDataSource,
            dispatcher
        )
}