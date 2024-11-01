package com.dreamsoftware.nimbustv.ui.screens.epg

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.CreateEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.CreateReminderException
import com.dreamsoftware.nimbustv.domain.exception.DeleteReminderByIdException
import com.dreamsoftware.nimbustv.domain.exception.FetchReminderByScheduleIdException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgChannelsDataException
import com.dreamsoftware.nimbustv.domain.exception.GetEpgDataException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.GetUserPreferencesException
import com.dreamsoftware.nimbustv.domain.exception.ParseEpgFailedException
import com.dreamsoftware.nimbustv.domain.exception.VerifyReminderByIdException

internal class EpgScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetProfileSelectedException -> R.string.get_profile_selected_exception
            is ParseEpgFailedException -> R.string.parse_epg_failed_exception
            is CreateEpgDataException -> R.string.create_epg_data_exception
            is GetEpgChannelsDataException -> R.string.get_epg_channels_data_exception
            is GetEpgDataException -> R.string.get_epg_data_exception
            is GetUserPreferencesException -> R.string.get_user_preferences_exception
            is CreateReminderException -> R.string.create_reminder_exception
            is VerifyReminderByIdException -> R.string.verify_reminder_by_id_exception
            is FetchReminderByScheduleIdException -> R.string.fetch_reminder_by_schedule_id_exception
            is DeleteReminderByIdException -> R.string.delete_reminder_by_id_exception
            else -> R.string.generic_error_exception
        }
    )
}