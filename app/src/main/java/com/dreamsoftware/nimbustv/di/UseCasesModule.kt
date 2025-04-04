package com.dreamsoftware.nimbustv.di

import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.repository.IChannelRepository
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository
import com.dreamsoftware.nimbustv.domain.repository.IPlaylistRepository
import com.dreamsoftware.nimbustv.domain.repository.IPreferencesRepository
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.repository.IRemindersRepository
import com.dreamsoftware.nimbustv.domain.service.IEpgParserService
import com.dreamsoftware.nimbustv.domain.service.IEpgSchedulerService
import com.dreamsoftware.nimbustv.domain.service.IPlaylistParserService
import com.dreamsoftware.nimbustv.domain.service.IReminderSchedulerService
import com.dreamsoftware.nimbustv.domain.usecase.AddFavoriteChannelUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CancelProfileRemindersUseCase
import com.dreamsoftware.nimbustv.domain.usecase.ChangeSecurePinUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CheckFavoriteChannelUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CreateEpgUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CreateProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.DeleteProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfileByIdUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfileSelectedUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfilesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfilesCountUseCase
import com.dreamsoftware.nimbustv.domain.usecase.HasProfileSelectedUseCase
import com.dreamsoftware.nimbustv.domain.usecase.HasProfilesCountUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CreatePlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CreateReminderUseCase
import com.dreamsoftware.nimbustv.domain.usecase.DeleteChannelByIdUseCase
import com.dreamsoftware.nimbustv.domain.usecase.DeleteEpgUseCase
import com.dreamsoftware.nimbustv.domain.usecase.DeletePlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelByIdUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetChannelsByPlaylistUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetEpgDataByIdUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetEpgListUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetFavoriteChannelsByProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetPlaylistsByProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetUserPreferencesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.RemoveChannelFromFavoritesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.RemoveReminderByScheduleUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SaveUserPreferencesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.ScheduleProfileRemindersUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SearchChannelsUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SelectProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SignOffUseCase
import com.dreamsoftware.nimbustv.domain.usecase.UpdateProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.VerifyPinUseCase
import com.dreamsoftware.nimbustv.domain.usecase.VerifyScheduleHasReminderUseCase
import com.dreamsoftware.nimbustv.domain.validation.IBusinessEntityValidator
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class UseCasesModule {

    @Provides
    @ViewModelScoped
    fun provideCreateProfileUseCase(
        profilesRepository: IProfilesRepository,
        createProfileRequestValidator: IBusinessEntityValidator<CreateProfileRequestBO>
    ): CreateProfileUseCase =
        CreateProfileUseCase(
            profilesRepository = profilesRepository,
            validator = createProfileRequestValidator
        )

    @Provides
    @ViewModelScoped
    fun provideDeleteProfileUseCase(
        epgRepository: IEpgRepository,
        profilesRepository: IProfilesRepository,
        epgSchedulerService: IEpgSchedulerService,
        cancelProfileRemindersUseCase: CancelProfileRemindersUseCase,
    ): DeleteProfileUseCase =
        DeleteProfileUseCase(
            epgRepository = epgRepository,
            profilesRepository = profilesRepository,
            epgSchedulerService = epgSchedulerService,
            cancelProfileRemindersUseCase = cancelProfileRemindersUseCase
        )


    @Provides
    @ViewModelScoped
    fun provideGetProfileByIdUseCase(
        profilesRepository: IProfilesRepository,
    ): GetProfileByIdUseCase =
        GetProfileByIdUseCase(
            profilesRepository = profilesRepository,
        )

    @Provides
    @ViewModelScoped
    fun provideGetProfileSelectedUseCase(
        profilesRepository: IProfilesRepository,
    ): GetProfileSelectedUseCase =
        GetProfileSelectedUseCase(
            profilesRepository = profilesRepository,
        )

    @Provides
    @ViewModelScoped
    fun provideGetProfilesUseCase(
        profilesRepository: IProfilesRepository
    ): GetProfilesUseCase =
        GetProfilesUseCase(
            profilesRepository = profilesRepository
        )

    @Provides
    @ViewModelScoped
    fun provideHasMultiplesProfilesUseCase(
        profilesRepository: IProfilesRepository
    ): GetProfilesCountUseCase =
        GetProfilesCountUseCase(
            profilesRepository = profilesRepository
        )

    @Provides
    @ViewModelScoped
    fun provideSelectProfileUseCase(
        profilesRepository: IProfilesRepository,
        cancelProfileRemindersUseCase: CancelProfileRemindersUseCase,
        scheduleProfileRemindersUseCase: ScheduleProfileRemindersUseCase
    ): SelectProfileUseCase =
        SelectProfileUseCase(
            profilesRepository = profilesRepository,
            cancelProfileRemindersUseCase = cancelProfileRemindersUseCase,
            scheduleProfileRemindersUseCase = scheduleProfileRemindersUseCase
        )

    @Provides
    @ViewModelScoped
    fun provideUpdateProfileUseCase(
        profilesRepository: IProfilesRepository,
        updatedProfileRequestValidator: IBusinessEntityValidator<UpdatedProfileRequestBO>
    ): UpdateProfileUseCase =
        UpdateProfileUseCase(
            profilesRepository = profilesRepository,
            validator = updatedProfileRequestValidator
        )

    @Provides
    @ViewModelScoped
    fun provideVerifyPinUseCase(
        profilesRepository: IProfilesRepository
    ): VerifyPinUseCase =
        VerifyPinUseCase(
            profilesRepository = profilesRepository
        )

    @Provides
    @ViewModelScoped
    fun provideChangeSecurePinUseCase(
        profilesRepository: IProfilesRepository,
        updateProfileRequestValidator: IBusinessEntityValidator<UpdatedProfileRequestBO>
    ): ChangeSecurePinUseCase =
        ChangeSecurePinUseCase(
            profilesRepository = profilesRepository,
            validator = updateProfileRequestValidator
        )

    @Provides
    @ViewModelScoped
    fun provideHasProfilesCountUseCase(
        profilesRepository: IProfilesRepository
    ): HasProfilesCountUseCase =
        HasProfilesCountUseCase(
            profilesRepository = profilesRepository
        )

    @Provides
    @ViewModelScoped
    fun provideSignOffUseCase(
        profilesRepository: IProfilesRepository,
        cancelProfileRemindersUseCase: CancelProfileRemindersUseCase,
    ): SignOffUseCase =
        SignOffUseCase(
            profilesRepository = profilesRepository,
            cancelProfileRemindersUseCase = cancelProfileRemindersUseCase
        )

    @Provides
    @ViewModelScoped
    fun provideHasProfileSelectedUseCase(
        profilesRepository: IProfilesRepository
    ): HasProfileSelectedUseCase =
        HasProfileSelectedUseCase(
            profilesRepository = profilesRepository
        )

    @Provides
    @ViewModelScoped
    fun provideCreatePlaylistUseCase(
        profilesRepository: IProfilesRepository,
        playlistParserService: IPlaylistParserService,
        playlistRepository: IPlaylistRepository,
        channelRepository: IChannelRepository
    ): CreatePlaylistUseCase =
        CreatePlaylistUseCase(
            profilesRepository = profilesRepository,
            playlistParserService = playlistParserService,
            playlistRepository = playlistRepository,
            channelRepository = channelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideGetPlaylistsByProfileUseCase(
        profilesRepository: IProfilesRepository,
        playlistRepository: IPlaylistRepository
    ): GetPlaylistsByProfileUseCase =
        GetPlaylistsByProfileUseCase(
            profilesRepository = profilesRepository,
            playlistRepository = playlistRepository,
        )


    @Provides
    @ViewModelScoped
    fun provideGetChannelsByPlaylistUseCase(
        channelRepository: IChannelRepository
    ): GetChannelsByPlaylistUseCase =
        GetChannelsByPlaylistUseCase(
            channelRepository = channelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideGetChannelByIdUseCase(
        channelRepository: IChannelRepository
    ): GetChannelByIdUseCase =
        GetChannelByIdUseCase(
            channelRepository = channelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideCreateEpgUseCase(
        profileRepository: IProfilesRepository,
        epgParserService: IEpgParserService,
        epgRepository: IEpgRepository,
        epgSchedulerService: IEpgSchedulerService
    ): CreateEpgUseCase =
        CreateEpgUseCase(
            profileRepository = profileRepository,
            epgParserService = epgParserService,
            epgRepository = epgRepository,
            epgSchedulerService = epgSchedulerService
        )

    @Provides
    @ViewModelScoped
    fun provideGetEpgDataByIdUseCase(
        epgRepository: IEpgRepository
    ): GetEpgDataByIdUseCase =
        GetEpgDataByIdUseCase(
            epgRepository = epgRepository
        )

    @Provides
    @ViewModelScoped
    fun provideGetEpgListUseCase(
        profileRepository: IProfilesRepository,
        epgRepository: IEpgRepository
    ): GetEpgListUseCase =
        GetEpgListUseCase(
            profileRepository = profileRepository,
            epgRepository = epgRepository
        )

    @Provides
    @ViewModelScoped
    fun provideDeleteEpgDataUseCase(
        epgRepository: IEpgRepository,
        epgSchedulerService: IEpgSchedulerService
    ): DeleteEpgUseCase =
        DeleteEpgUseCase(
            epgRepository = epgRepository,
            epgSchedulerService = epgSchedulerService
        )


    @Provides
    @ViewModelScoped
    fun provideAddFavoriteChannelUseCase(
        profileRepository: IProfilesRepository,
        channelRepository: IChannelRepository
    ): AddFavoriteChannelUseCase =
        AddFavoriteChannelUseCase(
            profileRepository = profileRepository,
            channelRepository = channelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteChannelsByProfileUseCase(
        profileRepository: IProfilesRepository,
        channelRepository: IChannelRepository
    ): GetFavoriteChannelsByProfileUseCase =
        GetFavoriteChannelsByProfileUseCase(
            profileRepository = profileRepository,
            channelRepository = channelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideRemoveChannelFromFavoritesChannelUseCase(
        profileRepository: IProfilesRepository,
        channelRepository: IChannelRepository
    ): RemoveChannelFromFavoritesUseCase =
        RemoveChannelFromFavoritesUseCase(
            profileRepository = profileRepository,
            channelRepository = channelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideDeletePlaylistUseCase(
        playlistRepository: IPlaylistRepository
    ): DeletePlaylistUseCase =
        DeletePlaylistUseCase(
            playlistRepository = playlistRepository
        )

    @Provides
    @ViewModelScoped
    fun provideCheckFavoriteChannelUseCase(
        profileRepository: IProfilesRepository,
        channelRepository: IChannelRepository
    ): CheckFavoriteChannelUseCase =
        CheckFavoriteChannelUseCase(
            profileRepository = profileRepository,
            channelRepository = channelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideDeleteChannelByIdUseCase(
        channelRepository: IChannelRepository
    ): DeleteChannelByIdUseCase =
        DeleteChannelByIdUseCase(
            channelRepository = channelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideSearchChannelsUseCase(
        profileRepository: IProfilesRepository,
        channelRepository: IChannelRepository
    ): SearchChannelsUseCase =
        SearchChannelsUseCase(
            profileRepository = profileRepository,
            channelRepository = channelRepository
        )

    @Provides
    @ViewModelScoped
    fun provideGetUserPreferencesUseCase(
        preferencesRepository: IPreferencesRepository
    ): GetUserPreferencesUseCase =
        GetUserPreferencesUseCase(preferencesRepository = preferencesRepository)

    @Provides
    @ViewModelScoped
    fun provideSaveUserPreferencesUseCase(
        preferencesRepository: IPreferencesRepository
    ): SaveUserPreferencesUseCase =
        SaveUserPreferencesUseCase(preferencesRepository = preferencesRepository)


    @Provides
    @ViewModelScoped
    fun provideCreateReminderUseCase(
        profileRepository: IProfilesRepository,
        remindersRepository: IRemindersRepository,
        reminderSchedulerService: IReminderSchedulerService
    ): CreateReminderUseCase =
        CreateReminderUseCase(
            profileRepository = profileRepository,
            remindersRepository = remindersRepository,
            reminderSchedulerService = reminderSchedulerService
        )

    @Provides
    @ViewModelScoped
    fun provideCancelProfileRemindersUseCase(
        reminderRepository: IRemindersRepository,
        reminderSchedulerService: IReminderSchedulerService
    ): CancelProfileRemindersUseCase =
        CancelProfileRemindersUseCase(
            reminderRepository = reminderRepository,
            reminderSchedulerService = reminderSchedulerService
        )

    @Provides
    @ViewModelScoped
    fun provideScheduleProfileRemindersUseCase(
        reminderRepository: IRemindersRepository,
        reminderSchedulerService: IReminderSchedulerService
    ): ScheduleProfileRemindersUseCase =
        ScheduleProfileRemindersUseCase(
            reminderRepository = reminderRepository,
            reminderSchedulerService = reminderSchedulerService
        )

    @Provides
    @ViewModelScoped
    fun provideVerifyScheduleHasReminderUseCase(
        remindersRepository: IRemindersRepository
    ): VerifyScheduleHasReminderUseCase =
        VerifyScheduleHasReminderUseCase(
            remindersRepository = remindersRepository
        )

    @Provides
    @ViewModelScoped
    fun provideRemoveReminderByScheduleUseCase(
        remindersRepository: IRemindersRepository,
        reminderSchedulerService: IReminderSchedulerService
    ): RemoveReminderByScheduleUseCase =
        RemoveReminderByScheduleUseCase(
            remindersRepository = remindersRepository,
            reminderSchedulerService = reminderSchedulerService
        )
}