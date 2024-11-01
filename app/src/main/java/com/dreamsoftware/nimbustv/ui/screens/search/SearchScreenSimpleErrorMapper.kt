package com.dreamsoftware.nimbustv.ui.screens.search

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.AddToFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.RemoveFromFavoritesException
import com.dreamsoftware.nimbustv.domain.exception.SearchChannelsException
import com.dreamsoftware.nimbustv.domain.exception.VerifyFavoriteChannelException

internal class SearchScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetProfileSelectedException -> R.string.get_profile_selected_exception
            is AddToFavoritesException -> R.string.add_to_favorites_exception
            is RemoveFromFavoritesException -> R.string.remove_from_favorites_exception
            is SearchChannelsException -> R.string.search_channels_exception
            is VerifyFavoriteChannelException -> R.string.verify_favorite_channel_exception
            else -> R.string.generic_error_exception
        }
    )
}