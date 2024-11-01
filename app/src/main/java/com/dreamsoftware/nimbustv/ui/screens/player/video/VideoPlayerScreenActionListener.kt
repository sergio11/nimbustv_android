package com.dreamsoftware.nimbustv.ui.screens.player.video

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface VideoPlayerScreenActionListener: IFudgeTvScreenActionListener {
    fun onAddFavoriteChannelClicked()
    fun onRemoveChannelFromFavorites()
    fun onOpenSettingsClicked()
}