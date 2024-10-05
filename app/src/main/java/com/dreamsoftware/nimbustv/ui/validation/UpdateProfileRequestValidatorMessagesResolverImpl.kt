package com.dreamsoftware.nimbustv.ui.validation

import android.content.Context
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.validation.IUpdateProfileRequestValidatorMessagesResolver

class UpdateProfileRequestValidatorMessagesResolverImpl(private val context: Context) :
    IUpdateProfileRequestValidatorMessagesResolver {

    override fun getInvalidAliasMessage(): String =
        context.getString(R.string.invalid_alias_message)
}