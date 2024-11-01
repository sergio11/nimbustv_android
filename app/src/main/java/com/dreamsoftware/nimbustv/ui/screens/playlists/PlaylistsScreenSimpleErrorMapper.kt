package com.dreamsoftware.nimbustv.ui.screens.playlists

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.GetPlaylistsByProfileException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.InsertPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.ParsePlayListFailedException
import com.dreamsoftware.nimbustv.domain.exception.SaveChannelsException

internal class PlaylistsScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetProfileSelectedException -> R.string.get_profile_selected_exception
            is GetPlaylistsByProfileException -> R.string.get_playlists_by_profile_exception
            is ParsePlayListFailedException -> R.string.parse_playlist_failed_exception
            is InsertPlaylistException -> R.string.insert_playlist_exception
            is SaveChannelsException -> R.string.save_channels_exception
            else -> R.string.generic_error_exception
        }
    )
}