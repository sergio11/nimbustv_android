package com.dreamsoftware.nimbustv.di

import com.dreamsoftware.nimbustv.domain.model.M3uEntryBO
import com.dreamsoftware.nimbustv.domain.service.IM3UService
import com.dreamsoftware.nimbustv.framework.m3u.M3UServiceImpl
import com.dreamsoftware.nimbustv.framework.mapper.M3UEntryMapper
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import net.bjoernpetersen.m3u.model.M3uEntry
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class FrameworkModule {

    @Provides
    @Singleton
    fun provideM3UEntryMapper(): IOneSideMapper<M3uEntry, M3uEntryBO> = M3UEntryMapper()

    @Provides
    @Singleton
    fun provideM3UService(
        mapper: IOneSideMapper<M3uEntry, M3uEntryBO>,
        @IoDispatcher dispatcher: CoroutineDispatcher
    ): IM3UService = M3UServiceImpl(mapper, dispatcher)
}