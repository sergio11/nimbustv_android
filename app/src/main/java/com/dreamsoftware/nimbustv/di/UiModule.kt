package com.dreamsoftware.nimbustv.di

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.favorites.FavoritesScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.profiles.save.SaveProfileScreenSimpleErrorMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UiModule {

    @Provides
    @ViewModelScoped
    @SaveProfileScreenErrorMapper
    fun provideSaveProfileScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        SaveProfileScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @FavoritesScreenErrorMapper
    fun provideFavoritesScreenSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        FavoritesScreenSimpleErrorMapper(context = context)
}