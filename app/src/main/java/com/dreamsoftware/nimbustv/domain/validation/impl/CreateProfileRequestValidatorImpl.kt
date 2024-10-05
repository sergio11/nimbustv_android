package com.dreamsoftware.nimbustv.domain.validation.impl

import com.dreamsoftware.nimbustv.domain.extensions.isProfileAliasNotValid
import com.dreamsoftware.nimbustv.domain.extensions.isSecurePinNotValid
import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.nimbustv.domain.validation.ICreateProfileRequestValidatorMessagesResolver

internal class CreateProfileRequestValidatorImpl(
    private val messagesResolver: ICreateProfileRequestValidatorMessagesResolver
) : IBusinessEntityValidator<CreateProfileRequestBO> {

    override fun validate(entity: CreateProfileRequestBO): Map<String, String> = buildMap {
        with(entity) {
            if (pin.toIntOrNull()?.isSecurePinNotValid() == true) {
                put(CreateProfileRequestBO.FIELD_PIN, messagesResolver.getInvalidPinMessage())
            }
            if (alias.isProfileAliasNotValid()) {
                put(CreateProfileRequestBO.FIELD_ALIAS, messagesResolver.getInvalidAliasMessage())
            }
        }
    }
}