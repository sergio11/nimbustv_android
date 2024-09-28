package com.dreamsoftware.nimbustv.di

import android.content.Context
import androidx.room.Room
import com.dreamsoftware.nimbustv.data.database.NimbusTvDatabase
import com.dreamsoftware.nimbustv.data.database.dao.ChannelDao
import com.dreamsoftware.nimbustv.data.database.datasource.IChannelLocalDataSource
import com.dreamsoftware.nimbustv.data.database.datasource.impl.ChannelLocalDataSourceImpl
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
}