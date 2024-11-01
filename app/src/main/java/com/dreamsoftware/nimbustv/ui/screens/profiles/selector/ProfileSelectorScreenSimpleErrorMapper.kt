package com.dreamsoftware.nimbustv.ui.screens.profiles.selector

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.GetProfileByIdException
import com.dreamsoftware.nimbustv.domain.exception.SelectProfileException
import com.dreamsoftware.nimbustv.domain.exception.VerifyPinException

internal class ProfileSelectorScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is VerifyPinException -> R.string.verify_pin_exception
            is SelectProfileException -> R.string.fetch_profiles_exception
            is GetProfileByIdException -> R.string.fetch_profiles_exception
            else -> R.string.generic_error_exception
        }
    )
}