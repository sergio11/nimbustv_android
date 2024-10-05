package com.dreamsoftware.nimbustv.di

import android.content.Context
import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.nimbustv.domain.validation.ICreateProfileRequestValidatorMessagesResolver
import com.dreamsoftware.nimbustv.domain.validation.IUpdateProfileRequestValidatorMessagesResolver
import com.dreamsoftware.nimbustv.domain.validation.impl.CreateProfileRequestValidatorImpl
import com.dreamsoftware.nimbustv.domain.validation.impl.UpdateProfileRequestValidatorImpl
import com.dreamsoftware.nimbustv.ui.validation.CreateProfileRequestValidatorMessagesResolverImpl
import com.dreamsoftware.nimbustv.ui.validation.UpdateProfileRequestValidatorMessagesResolverImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
class ValidatorModule {

    @Provides
    @ViewModelScoped
    fun provideCreateProfileRequestValidatorMessagesResolver(
        @ApplicationContext context: Context
    ): ICreateProfileRequestValidatorMessagesResolver =
        CreateProfileRequestValidatorMessagesResolverImpl(context)

    @Provides
    @ViewModelScoped
    fun provideIUpdateProfileRequestValidatorMessagesResolver(
        @ApplicationContext context: Context
    ): IUpdateProfileRequestValidatorMessagesResolver =
        UpdateProfileRequestValidatorMessagesResolverImpl(context)

    @Provides
    @ViewModelScoped
    fun provideCreateProfileRequestValidator(
        messagesResolver: ICreateProfileRequestValidatorMessagesResolver
    ): IBusinessEntityValidator<CreateProfileRequestBO> =
        CreateProfileRequestValidatorImpl(messagesResolver)

    @Provides
    @ViewModelScoped
    fun provideUpdateProfileRequestValidator(
        messagesResolver: IUpdateProfileRequestValidatorMessagesResolver
    ): IBusinessEntityValidator<UpdatedProfileRequestBO> =
        UpdateProfileRequestValidatorImpl(messagesResolver)
}