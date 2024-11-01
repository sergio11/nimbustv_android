package com.dreamsoftware.nimbustv.di

import javax.inject.Qualifier

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class DefaultDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class IoDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class MainDispatcher

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class MainImmediateDispatcher

@Qualifier
@Retention(AnnotationRetention.RUNTIME)
annotation class ApplicationScope

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class FavoritesScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SearchChannelsScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EpgScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlaylistsScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class PlaylistDetailScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class EpgSourcesScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class HomeScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SaveProfileScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SettingsScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ChangeSecurePinScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class DeleteProfileScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProfileManagementScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class SecurePinScreenErrorMapper

@Qualifier
@Retention(AnnotationRetention.BINARY)
annotation class ProfileSelectorScreenErrorMapper

