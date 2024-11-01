package com.dreamsoftware.nimbustv.ui.screens.profiles.management

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.FetchProfilesException

internal class ProfileManagementScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is FetchProfilesException -> R.string.fetch_profiles_exception
            else -> R.string.generic_error_exception
        }
    )
}