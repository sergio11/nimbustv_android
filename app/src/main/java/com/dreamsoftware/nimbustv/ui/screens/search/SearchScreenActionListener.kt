package com.dreamsoftware.nimbustv.ui.screens.search

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener
import com.dreamsoftware.nimbustv.domain.model.ChannelBO

interface SearchScreenActionListener: IFudgeTvScreenActionListener {

    fun onOpenChannelDetail(channel: ChannelBO)
    fun onCloseDetail()
    fun onPlayChannel(channel: ChannelBO)
    fun onRemoveFromFavorites(channel: ChannelBO)
    fun onKeyPressed(key: String)
    fun onClearPressed()
    fun onBackSpacePressed()
    fun onSpaceBarPressed()
    fun onSearchPressed()
}