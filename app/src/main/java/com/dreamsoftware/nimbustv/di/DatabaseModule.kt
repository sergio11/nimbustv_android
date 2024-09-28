package com.dreamsoftware.nimbustv.di

import android.content.Context
import androidx.room.Room
import com.dreamsoftware.nimbustv.data.database.NimbusTvDatabase
import com.dreamsoftware.nimbustv.data.database.dao.ChannelDao
import com.dreamsoftware.nimbustv.data.database.dao.PlayListDao
import com.dreamsoftware.nimbustv.data.database.dao.ProfileDao
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IPlayListLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IProfileLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.ChannelLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.datasource.impl.PlaylistLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.datasource.impl.ProfileLocalDataSourceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideRoomInstance(@ApplicationContext context: Context) = Room.databaseBuilder(
        context,
        NimbusTvDatabase::class.java,
        "nimbus_tv_database"
    ).build()

    @Singleton
    @Provides
    fun provideChannelDao(db: NimbusTvDatabase) = db.channelDao()

    @Singleton
    @Provides
    fun provideChannelsDataSource(
        channelDao: ChannelDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IChannelLocalDataSource = ChannelLocalDataSourceImpl(channelDao, dispatcher)

    @Singleton
    @Provides
    fun provideProfilesDataSource(
        profileDao: ProfileDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IProfileLocalDataSource = ProfileLocalDataSourceImpl(profileDao, dispatcher)

    @Singleton
    @Provides
    fun providePlayListDataSource(
        playListDao: PlayListDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IPlayListLocalDataSource = PlaylistLocalDataSourceImpl(playListDao, dispatcher)
}