package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.exception.InvalidDataException
import com.dreamsoftware.nimbustv.domain.exception.UserProfilesLimitReachedException
import com.dreamsoftware.nimbustv.domain.model.AvatarTypeEnum
import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.validation.IBusinessEntityValidator
import java.util.UUID

class CreateProfileUseCase(
    private val profilesRepository: IProfilesRepository,
    private val validator: IBusinessEntityValidator<CreateProfileRequestBO>
): FudgeTvUseCaseWithParams<CreateProfileUseCase.Params, ProfileBO>() {

    private companion object {
        const val MAX_PROFILES_BY_USER = 4
    }

    override suspend fun onExecuted(params: Params): ProfileBO = with(params) {
        toCreateProfileRequestBO().let { createProfileRequestBO ->
            validator.validate(createProfileRequestBO).takeIf { it.isNotEmpty() }?.let { errors ->
                throw InvalidDataException(errors, "Invalid data provided")
            } ?: run {
                with(profilesRepository) {
                    if(countProfiles() < MAX_PROFILES_BY_USER) {
                        createProfile(createProfileRequestBO)
                    } else {
                        throw UserProfilesLimitReachedException(
                            maxProfilesLimit = MAX_PROFILES_BY_USER,
                            message = "You has reached profile creation limit"
                        )
                    }
                }
            }
        }
    }

    private fun Params.toCreateProfileRequestBO() = CreateProfileRequestBO(
        id = UUID.randomUUID().toString(),
        pin = pin,
        alias = alias,
        avatarType = avatarType ?: AvatarTypeEnum.BOY,
    )

    data class Params(
        val alias: String,
        val pin: String,
        val avatarType: AvatarTypeEnum?
    )
}