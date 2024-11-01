package com.dreamsoftware.nimbustv.ui.screens.profiles.save

import android.content.Context
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.InvalidDataException
import com.dreamsoftware.nimbustv.domain.exception.UserProfilesLimitReachedException
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.domain.exception.CreateProfileException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileByIdException
import com.dreamsoftware.nimbustv.domain.exception.UpdateProfileException

internal class SaveProfileScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {
    override fun mapToMessage(ex: Throwable): String = with(context) {
        when (ex) {
            is GetProfileByIdException -> getString(R.string.save_profile_form_invalid_data_provided)
            is InvalidDataException -> getString(R.string.save_profile_form_invalid_data_provided)
            is UserProfilesLimitReachedException -> getString(
                R.string.save_profile_limit_reached_error,
                ex.maxProfilesLimit
            )

            is CreateProfileException -> getString(R.string.save_profile_form_invalid_data_provided)
            is UpdateProfileException -> getString(R.string.save_profile_form_invalid_data_provided)
            else -> getString(R.string.generic_error_exception)
        }
    }
}