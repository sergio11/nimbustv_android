package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCaseWithParams
import com.dreamsoftware.nimbustv.domain.exception.InvalidDataException
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.domain.validation.IBusinessEntityValidator

class ChangeSecurePinUseCase(
    private val profilesRepository: IProfilesRepository,
    private val validator: IBusinessEntityValidator<UpdatedProfileRequestBO>
): FudgeTvUseCaseWithParams<ChangeSecurePinUseCase.Params, ProfileBO>() {

    override suspend fun onExecuted(params: Params): ProfileBO = with(params) {
        with(profilesRepository) {
            verifyPin(profileId, currentSecurePin)
            toUpdatedProfileRequestBO().let { updatedProfileRequestBO ->
                validator.validate(updatedProfileRequestBO).takeIf { it.isNotEmpty() }?.let { errors ->
                    throw InvalidDataException(errors, "Invalid data provided")
                } ?: run {
                    updateProfile(profileId, updatedProfileRequestBO)
                }
            }
        }
    }

    private fun Params.toUpdatedProfileRequestBO() = UpdatedProfileRequestBO(pin = newSecurePin)

    data class Params(
        val profileId: Long,
        val currentSecurePin: Int,
        val newSecurePin: Int
    )
}