package com.dreamsoftware.nimbustv.ui.screens.home

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.AddToFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistAndCategoryException
import com.dreamsoftware.nimbustv.domain.exception.GetChannelsByPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.GetPlaylistsByProfileException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.InsertPlaylistException
import com.dreamsoftware.nimbustv.domain.exception.ParsePlayListFailedException
import com.dreamsoftware.nimbustv.domain.exception.RemoveFromFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.SaveChannelsException
import com.dreamsoftware.nimbustv.domain.exception.VerifyFavoriteChannelException

internal class HomeScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetProfileSelectedException -> R.string.get_profile_selected_exception
            is ParsePlayListFailedException -> R.string.parse_playlist_failed_exception
            is InsertPlaylistException -> R.string.insert_playlist_exception
            is SaveChannelsException -> R.string.save_channels_exception
            is GetPlaylistsByProfileException -> R.string.get_playlists_by_profile_exception
            is GetChannelsByPlaylistAndCategoryException -> R.string.get_channels_by_playlist_and_category_exception
            is GetChannelsByPlaylistException -> R.string.get_channels_by_playlist_exception
            is AddToFavoritesException -> R.string.add_to_favorites_exception
            is RemoveFromFavoritesException -> R.string.remove_from_favorites_exception
            is VerifyFavoriteChannelException -> R.string.verify_favorite_channel_exception
            else -> R.string.generic_error_exception
        }
    )
}