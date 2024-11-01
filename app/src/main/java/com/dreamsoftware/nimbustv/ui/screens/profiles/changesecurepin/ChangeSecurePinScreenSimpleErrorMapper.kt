package com.dreamsoftware.nimbustv.ui.screens.profiles.changesecurepin

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.GetProfileByIdException
import com.dreamsoftware.nimbustv.domain.exception.InvalidDataException
import com.dreamsoftware.nimbustv.domain.exception.UpdateProfileException
import com.dreamsoftware.nimbustv.domain.exception.VerifyPinException

internal class ChangeSecurePinScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetProfileByIdException -> R.string.get_profile_by_id_exception
            is InvalidDataException -> R.string.invalid_data_exception
            is VerifyPinException -> R.string.verify_pin_exception
            is UpdateProfileException -> R.string.update_profile_exception
            else -> R.string.generic_error_exception
        }
    )
}