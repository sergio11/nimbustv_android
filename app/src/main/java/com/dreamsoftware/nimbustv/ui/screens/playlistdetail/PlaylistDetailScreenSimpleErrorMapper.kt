package com.dreamsoftware.nimbustv.ui.screens.playlistdetail

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.DeleteChannelByIdException
import com.dreamsoftware.nimbustv.domain.exception.DeletePlaylistsException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistAndCategoryException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistException

class PlaylistDetailScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetChannelsByPlaylistAndCategoryException -> R.string.get_channels_by_playlist_and_category_exception
            is GetChannelsByPlaylistException -> R.string.get_channels_by_playlist_exception
            is DeletePlaylistsException -> R.string.delete_playlists_exception
            is DeleteChannelByIdException -> R.string.delete_channel_by_id_exception
            else -> R.string.generic_error_exception
        }
    )
}
