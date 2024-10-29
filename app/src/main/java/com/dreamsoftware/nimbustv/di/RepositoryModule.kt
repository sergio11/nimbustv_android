package com.dreamsoftware.nimbustv.di

import com.dreamsoftware.nimbustv.data.database.datasource.IChannelEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IFavoriteChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IPlayListLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IProfileLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelScheduleEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IReminderLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.EpgEntity
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.data.database.entity.ChannelScheduleEntity
import com.dreamsoftware.nimbustv.data.database.entity.ReminderEntity
import com.dreamsoftware.nimbustv.data.preferences.datasource.IProfileSessionDataSource
import com.dreamsoftware.nimbustv.data.preferences.datasource.IUserPreferencesDataSource
import com.dreamsoftware.nimbustv.data.preferences.dto.UserPreferencesDTO
import com.dreamsoftware.nimbustv.data.repository.impl.ChannelsRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.impl.EpgRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.impl.PlaylistRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.impl.PreferencesRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.impl.ProfilesRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.impl.RemindersRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.mapper.ChannelsMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.CreatePlaylistMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.CreateProfileMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.CreateRemindersMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.EpgDataInput
import com.dreamsoftware.nimbustv.data.repository.mapper.EpgDataMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.EpgMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.PlaylistMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.ProfileMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.RemindersMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.SaveChannelsMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.SaveEpgChannelDataMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.SaveEpgMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.SaveEpgProgrammeDataMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.UpdatePlaylistData
import com.dreamsoftware.nimbustv.data.repository.mapper.UpdatePlaylistMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.UpdateProfileMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.UserPreferencesMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.CreateEpgBO
import com.dreamsoftware.nimbustv.domain.model.CreateEpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.CreateEpgScheduleBO
import com.dreamsoftware.nimbustv.domain.model.CreatePlayListBO
import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.CreateReminderBO
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.domain.model.EpgChannelBO
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.model.ReminderBO
import com.dreamsoftware.nimbustv.domain.model.SaveChannelBO
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.UserPreferenceBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IPlaylistRepository
import com.dreamsoftware.nimbustv.domain.repository.IPreferencesRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.repository.IRemindersRepository
import com.dreamsoftware.nimbustv.utils.IMapper
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
    fun provideSaveEpgDataMapper(): IOneSideMapper<CreateEpgBO, EpgEntity> = SaveEpgMapper()

    @Provides
    @Singleton
    fun provideSaveEpgChannelDataMapper(): IOneSideMapper<CreateEpgChannelBO, ChannelEpgEntity> = SaveEpgChannelDataMapper()

    @Provides
    @Singleton
    fun provideSaveEpgProgrammeDataMapper(): IOneSideMapper<CreateEpgScheduleBO, ChannelScheduleEntity> = SaveEpgProgrammeDataMapper()

    @Provides
    @Singleton
    fun provideEpgDataMapper(): IOneSideMapper<EpgDataInput, List<EpgChannelBO>> = EpgDataMapper()

    @Provides
    @Singleton
    fun provideEpgMapper(): IOneSideMapper<EpgEntity, EpgBO> = EpgMapper()

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
    fun provideCreatePlaylistMapper(): IOneSideMapper<CreatePlayListBO, PlayListEntity> =
        CreatePlaylistMapper()

    @Provides
    @Singleton
    fun provideUpdatePlaylistMapper(): IOneSideMapper<UpdatePlaylistData, PlayListEntity> =
        UpdatePlaylistMapper()

    @Provides
    @Singleton
    fun providePlaylistMapper(): IOneSideMapper<PlayListEntity, PlayListBO> =
        PlaylistMapper()

    @Provides
    @Singleton
    fun provideSaveChannelsMapper(): IOneSideMapper<SaveChannelBO, ChannelEntity> =
        SaveChannelsMapper()

    @Provides
    @Singleton
    fun provideChannelsMapper(): IOneSideMapper<ChannelEntity, ChannelBO> =
        ChannelsMapper()

    @Provides
    @Singleton
    fun provideRemindersMapper(): IOneSideMapper<ReminderEntity, ReminderBO> =
        RemindersMapper()

    @Provides
    @Singleton
    fun provideCreateRemindersMapper(): IOneSideMapper<CreateReminderBO, ReminderEntity> =
        CreateRemindersMapper()

    @Provides
    @Singleton
    fun provideUserPreferencesMapper(): IMapper<UserPreferencesDTO, UserPreferenceBO> = UserPreferencesMapper()

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

    @Provides
    @Singleton
    fun providePlaylistRepository(
        playListLocalDataSource: IPlayListLocalDataSource,
        createPlaylistMapper: IOneSideMapper<CreatePlayListBO, PlayListEntity>,
        updatePlaylistMapper: IOneSideMapper<UpdatePlaylistData, PlayListEntity>,
        playlistMapper: IOneSideMapper<PlayListEntity, PlayListBO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IPlaylistRepository =
        PlaylistRepositoryImpl(
            playListLocalDataSource,
            createPlaylistMapper,
            updatePlaylistMapper,
            playlistMapper,
            dispatcher
        )


    @Provides
    @Singleton
    fun provideChannelRepository(
        channelLocalDataSource: IChannelLocalDataSource,
        favoriteChannelLocalDataSource: IFavoriteChannelLocalDataSource,
        saveChannelsMapper: IOneSideMapper<SaveChannelBO, ChannelEntity>,
        channelsMapper: IOneSideMapper<ChannelEntity, ChannelBO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IChannelRepository =
        ChannelsRepositoryImpl(
            channelLocalDataSource,
            favoriteChannelLocalDataSource,
            saveChannelsMapper,
            channelsMapper,
            dispatcher
        )

    @Provides
    @Singleton
    fun provideEpgRepository(
        programmeLocalDataSource: IChannelScheduleEpgLocalDataSource,
        channelLocalDataSource: IChannelEpgLocalDataSource,
        epgLocalDataSource: IEpgLocalDataSource,
        saveEpgDataMapper: IOneSideMapper<CreateEpgBO, EpgEntity>,
        saveEpgChannelDataMapper: IOneSideMapper<CreateEpgChannelBO, ChannelEpgEntity>,
        saveEpgProgrammeDataMapper: IOneSideMapper<CreateEpgScheduleBO, ChannelScheduleEntity>,
        epgDataMapper: IOneSideMapper<EpgEntity, EpgBO>,
        epgChannelsDataMapper: IOneSideMapper<EpgDataInput, List<EpgChannelBO>>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IEpgRepository =
        EpgRepositoryImpl(
            programmeLocalDataSource,
            channelLocalDataSource,
            epgLocalDataSource,
            saveEpgDataMapper,
            saveEpgChannelDataMapper,
            saveEpgProgrammeDataMapper,
            epgDataMapper,
            epgChannelsDataMapper,
            dispatcher
        )

    @Provides
    @Singleton
    fun providePreferencesRepository(
        userPreferencesDataSource: IUserPreferencesDataSource,
        userPreferencesMapper: IMapper<UserPreferencesDTO, UserPreferenceBO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IPreferencesRepository =
        PreferencesRepositoryImpl(
            userPreferencesDataSource,
            userPreferencesMapper,
            dispatcher
        )

    @Provides
    @Singleton
    fun provideRemindersRepository(
        reminderLocalDataSource: IReminderLocalDataSource,
        remindersMapper: IOneSideMapper<ReminderEntity, ReminderBO>,
        createReminderMapper: IOneSideMapper<CreateReminderBO, ReminderEntity>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IRemindersRepository =
        RemindersRepositoryImpl(
            reminderLocalDataSource,
            remindersMapper,
            createReminderMapper,
            dispatcher
        )
}