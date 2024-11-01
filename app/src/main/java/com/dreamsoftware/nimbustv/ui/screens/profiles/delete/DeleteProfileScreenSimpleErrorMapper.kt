package com.dreamsoftware.nimbustv.ui.screens.profiles.delete

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.DeleteProfileException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileByIdException
import com.dreamsoftware.nimbustv.domain.exception.InvalidDataException
import com.dreamsoftware.nimbustv.domain.exception.UpdateProfileException
import com.dreamsoftware.nimbustv.domain.exception.VerifyPinException

internal class DeleteProfileScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetProfileByIdException -> R.string.get_profile_by_id_exception
            is GetEpgDataException -> R.string.get_epg_data_exception
            is DeleteProfileException -> R.string.delete_profile_exception
            else -> R.string.generic_error_exception
        }
    )
}