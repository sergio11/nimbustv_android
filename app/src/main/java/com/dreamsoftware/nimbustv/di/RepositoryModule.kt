package com.dreamsoftware.nimbustv.di

import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IPlayListLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IProfileLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ChannelEntity
import com.dreamsoftware.nimbustv.data.database.entity.PlayListEntity
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.data.preferences.datasource.IProfileSessionDataSource
import com.dreamsoftware.nimbustv.data.repository.impl.ChannelsRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.impl.PlaylistRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.impl.ProfilesRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.mapper.ChannelsMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.CreatePlaylistMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.CreateProfileMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.PlaylistMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.ProfileMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.SaveChannelsMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.UpdatePlaylistData
import com.dreamsoftware.nimbustv.data.repository.mapper.UpdatePlaylistMapper
import com.dreamsoftware.nimbustv.data.repository.mapper.UpdateProfileMapper
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.CreatePlayListBO
import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.PlayListBO
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.model.SaveChannelBO
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.domain.repository.IPlaylistRepository
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
        saveChannelsMapper: IOneSideMapper<SaveChannelBO, ChannelEntity>,
        channelsMapper: IOneSideMapper<ChannelEntity, ChannelBO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IChannelRepository =
        ChannelsRepositoryImpl(
            channelLocalDataSource,
            saveChannelsMapper,
            channelsMapper,
            dispatcher
        )
}