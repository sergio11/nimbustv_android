package com.dreamsoftware.nimbustv.di

import android.content.Context
import androidx.room.Room
import com.dreamsoftware.nimbustv.data.database.NimbusTvDatabase
import com.dreamsoftware.nimbustv.data.database.dao.ChannelDao
import com.dreamsoftware.nimbustv.data.database.dao.ChannelEpgDao
import com.dreamsoftware.nimbustv.data.database.dao.EpgDao
import com.dreamsoftware.nimbustv.data.database.dao.FavoriteChannelDao
import com.dreamsoftware.nimbustv.data.database.dao.PlayListDao
import com.dreamsoftware.nimbustv.data.database.dao.ProfileDao
import com.dreamsoftware.nimbustv.data.database.dao.ProgrammeDao
import com.dreamsoftware.nimbustv.data.database.dao.ReminderDao
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IFavoriteChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IPlayListLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IProfileLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelScheduleEpgLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.IReminderLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.ChannelEpgLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.datasource.impl.ChannelLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.datasource.impl.EpgLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.datasource.impl.FavoriteChannelLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.datasource.impl.PlaylistLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.datasource.impl.ProfileLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.datasource.impl.ChannelScheduleEpgLocalDataSourceImpl
import com.dreamsoftware.nimbustv.data.database.datasource.impl.ReminderLocalDataSourceImpl
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
    fun provideProfilesDao(db: NimbusTvDatabase) = db.profileDao()

    @Singleton
    @Provides
    fun providePlayListDao(db: NimbusTvDatabase) = db.playListM3UDao()

    @Singleton
    @Provides
    fun provideEpgDao(db: NimbusTvDatabase) = db.epgDao()

    @Singleton
    @Provides
    fun provideProgrammeDao(db: NimbusTvDatabase) = db.programmeDao()

    @Singleton
    @Provides
    fun provideChannelEpgDao(db: NimbusTvDatabase) = db.channelEpgDao()

    @Singleton
    @Provides
    fun provideFavoriteChannelDao(db: NimbusTvDatabase) = db.favoriteChannelDao()

    @Singleton
    @Provides
    fun provideReminderDao(db: NimbusTvDatabase) = db.reminderDao()

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

    @Singleton
    @Provides
    fun provideProgrammeLocalDataSource(
        programmeDao: ProgrammeDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IChannelScheduleEpgLocalDataSource = ChannelScheduleEpgLocalDataSourceImpl(programmeDao, dispatcher)

    @Singleton
    @Provides
    fun provideChannelEpgLocalDataSource(
        channelEpgDao: ChannelEpgDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IChannelEpgLocalDataSource = ChannelEpgLocalDataSourceImpl(channelEpgDao, dispatcher)

    @Singleton
    @Provides
    fun provideFavoriteChannelLocalDataSource(
        favoriteChannelDao: FavoriteChannelDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IFavoriteChannelLocalDataSource = FavoriteChannelLocalDataSourceImpl(favoriteChannelDao, dispatcher)

    @Singleton
    @Provides
    fun provideEpgLocalDataSource(
        epgDao: EpgDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IEpgLocalDataSource = EpgLocalDataSourceImpl(epgDao, dispatcher)

    @Singleton
    @Provides
    fun provideReminderLocalDataSource(
        reminderDao: ReminderDao,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IReminderLocalDataSource = ReminderLocalDataSourceImpl(reminderDao, dispatcher)
}