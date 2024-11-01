package com.dreamsoftware.nimbustv.ui.screens.settings

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.GetUserPreferencesException
import com.dreamsoftware.nimbustv.domain.exception.SaveUserPreferencesException

internal class SettingsScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetUserPreferencesException -> R.string.get_profile_selected_exception
            is SaveUserPreferencesException -> R.string.parse_playlist_failed_exception
            else -> R.string.generic_error_exception
        }
    )
}