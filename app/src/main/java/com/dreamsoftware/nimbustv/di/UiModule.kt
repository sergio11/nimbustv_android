package com.dreamsoftware.nimbustv.di

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.epg.EpgScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.epgsources.EpgSourcesScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.favorites.FavoritesScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.home.HomeScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.playlistdetail.PlaylistDetailScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.playlists.PlaylistsScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.profiles.changesecurepin.ChangeSecurePinScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.profiles.delete.DeleteProfileScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.profiles.management.ProfileManagementScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.profiles.save.SaveProfileScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.profiles.secure.SecurePinScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.profiles.selector.ProfileSelectorScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.search.SearchScreenSimpleErrorMapper
import com.dreamsoftware.nimbustv.ui.screens.settings.SettingsScreenSimpleErrorMapper
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

    @Provides
    @ViewModelScoped
    @EpgScreenErrorMapper
    fun provideEpgScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        EpgScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @PlaylistsScreenErrorMapper
    fun providePlaylistsScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        PlaylistsScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @PlaylistDetailScreenErrorMapper
    fun providePlaylistDetailScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        PlaylistDetailScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @SearchChannelsScreenErrorMapper
    fun provideSearchChannelsScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        SearchScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @EpgSourcesScreenErrorMapper
    fun provideEpgSourcesScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        EpgSourcesScreenSimpleErrorMapper(context = context)


    @Provides
    @ViewModelScoped
    @HomeScreenErrorMapper
    fun provideHomeScreenSimpleErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        HomeScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @SettingsScreenErrorMapper
    fun provideSettingsScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        SettingsScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @ChangeSecurePinScreenErrorMapper
    fun provideChangeSecurePinScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        ChangeSecurePinScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @DeleteProfileScreenErrorMapper
    fun provideDeleteProfileScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        DeleteProfileScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @ProfileManagementScreenErrorMapper
    fun provideProfileManagementScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        ProfileManagementScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @SecurePinScreenErrorMapper
    fun provideSecurePinScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        SecurePinScreenSimpleErrorMapper(context = context)

    @Provides
    @ViewModelScoped
    @ProfileSelectorScreenErrorMapper
    fun provideProfileSelectorScreenErrorMapper(
        @ApplicationContext context: Context
    ): IFudgeTvErrorMapper =
        ProfileSelectorScreenSimpleErrorMapper(context = context)
}

