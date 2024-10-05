package com.dreamsoftware.nimbustv.domain.validation.impl

import com.dreamsoftware.nimbustv.domain.extensions.isProfileAliasNotValid
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.validation.IBusinessEntityValidator
import com.dreamsoftware.nimbustv.domain.validation.IUpdateProfileRequestValidatorMessagesResolver

internal class UpdateProfileRequestValidatorImpl(
    private val messagesResolver: IUpdateProfileRequestValidatorMessagesResolver
): IBusinessEntityValidator<UpdatedProfileRequestBO> {

    override fun validate(entity: UpdatedProfileRequestBO): Map<String, String> = buildMap {
        with(entity) {
            if(alias != null && alias.isProfileAliasNotValid()) {
                put(UpdatedProfileRequestBO.FIELD_ALIAS, messagesResolver.getInvalidAliasMessage())
            }
        }
    }
}