package com.dreamsoftware.nimbustv.di

import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.usecase.ChangeSecurePinUseCase
import com.dreamsoftware.nimbustv.domain.usecase.CreateProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.DeleteProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfileByIdUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfileSelectedUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfilesUseCase
import com.dreamsoftware.nimbustv.domain.usecase.GetProfilesCountUseCase
import com.dreamsoftware.nimbustv.domain.usecase.SelectProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.UpdateProfileUseCase
import com.dreamsoftware.nimbustv.domain.usecase.VerifyPinUseCase
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
        profilesRepository: IProfilesRepository,
    ): DeleteProfileUseCase =
        DeleteProfileUseCase(
            profilesRepository = profilesRepository,
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
        profilesRepository: IProfilesRepository
    ): SelectProfileUseCase =
        SelectProfileUseCase(
            profilesRepository = profilesRepository
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
}