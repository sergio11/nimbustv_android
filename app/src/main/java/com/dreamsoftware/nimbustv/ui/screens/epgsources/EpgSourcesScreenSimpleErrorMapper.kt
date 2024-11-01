package com.dreamsoftware.nimbustv.ui.screens.epgsources

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.CreateEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.DeleteEpgException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.ParseEpgFailedException

internal class EpgSourcesScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetProfileSelectedException -> R.string.get_profile_selected_exception
            is GetEpgDataException -> R.string.get_epg_data_exception
            is ParseEpgFailedException -> R.string.parse_epg_failed_exception
            is CreateEpgDataException -> R.string.create_epg_data_exception
            is DeleteEpgException -> R.string.delete_epg_exception
            else -> R.string.generic_error_exception
        }
    )
}
