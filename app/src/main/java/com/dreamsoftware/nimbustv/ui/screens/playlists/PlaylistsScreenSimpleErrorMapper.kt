package com.dreamsoftware.nimbustv.ui.screens.playlists

import android.content.Context
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.InvalidDataException
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper

class PlaylistsScreenSimpleErrorMapper(
    private val context: Context
): IFudgeTvErrorMapper {
    override fun mapToMessage(ex: Throwable): String = context.getString(when(ex) {
        is InvalidDataException -> R.string.generic_form_invalid_data_provided
        else -> R.string.generic_error_exception
    })
}