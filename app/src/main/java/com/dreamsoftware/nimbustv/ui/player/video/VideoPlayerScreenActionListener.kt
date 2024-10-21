package com.dreamsoftware.nimbustv.ui.player.video

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface VideoPlayerScreenActionListener: IFudgeTvScreenActionListener {
    fun onAddFavoriteChannelClicked()
    fun onRemoveChannelFromFavorites()
    fun onOpenSettingsClicked()
}