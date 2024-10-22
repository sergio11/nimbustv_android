package com.dreamsoftware.nimbustv.di

import android.content.Context
import androidx.work.WorkManager
import com.dreamsoftware.nimbustv.domain.model.M3uEntryBO
import com.dreamsoftware.nimbustv.domain.service.IEpgParserService
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService
import com.dreamsoftware.nimbustv.domain.service.IPlaylistParserService
import com.dreamsoftware.nimbustv.framework.epg.parser.EpgParserServiceImpl
import com.dreamsoftware.nimbustv.framework.epg.scheduler.WorkManagerEpgSchedulerImpl
import com.dreamsoftware.nimbustv.framework.m3u.M3UServiceImpl
import com.dreamsoftware.nimbustv.framework.m3u.model.M3uEntry
import com.dreamsoftware.nimbustv.framework.mapper.M3UEntryMapper
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import okhttp3.OkHttpClient
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {

    @Provides
    @Singleton
    fun provideM3UEntryMapper(): IOneSideMapper<M3uEntry, M3uEntryBO> = M3UEntryMapper()

    @Provides
    @Singleton
    fun provideOkHttpClient(): OkHttpClient = OkHttpClient()

    @Provides
    @Singleton
    fun provideWorkerManager(
        @ApplicationContext context: Context
    ): WorkManager = WorkManager.getInstance(context)

    @Provides
    @Singleton
    fun provideM3UService(
        mapper: IOneSideMapper<M3uEntry, M3uEntryBO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IPlaylistParserService = M3UServiceImpl(mapper, dispatcher)

    @Provides
    @Singleton
    fun provideEpgParserService(
        client: OkHttpClient,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IEpgParserService = EpgParserServiceImpl(client, dispatcher)

    @Provides
    @Singleton
    fun provideEpgSchedulerService(
        workManager: WorkManager
    ): IEpgSchedulerService = WorkManagerEpgSchedulerImpl(workManager)
}