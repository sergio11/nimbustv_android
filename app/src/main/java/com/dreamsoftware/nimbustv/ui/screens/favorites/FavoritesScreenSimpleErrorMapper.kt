package com.dreamsoftware.nimbustv.ui.screens.favorites

import android.content.Context
import com.dreamsoftware.fudge.core.IFudgeTvErrorMapper
import com.dreamsoftware.nimbustv.R
import com.dreamsoftware.nimbustv.domain.exception.GetFavoriteChannelsByProfileIdException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.RemoveFromFavoritesException

internal class FavoritesScreenSimpleErrorMapper(
    private val context: Context
) : IFudgeTvErrorMapper {

    override fun mapToMessage(ex: Throwable): String = context.getString(
        when (ex) {
            is GetProfileSelectedException -> R.string.get_profile_selected_exception
            is RemoveFromFavoritesException -> R.string.remove_from_favorites_exception
            is GetFavoriteChannelsByProfileIdException -> R.string.get_favorite_channels_by_profile_id_exception
            else -> R.string.generic_error_exception
        }
    )
}
