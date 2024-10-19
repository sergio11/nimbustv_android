package com.dreamsoftware.nimbustv.ui.screens.favorites

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener
import com.dreamsoftware.nimbustv.domain.model.ChannelBO

interface FavoritesScreenActionListener: IFudgeTvScreenActionListener {

    fun onOpenChannelDetail(channel: ChannelBO)
    fun onCloseDetail()
    fun onPlayChannel(channel: ChannelBO)
    fun onRemoveFromFavorites(channel: ChannelBO)
}