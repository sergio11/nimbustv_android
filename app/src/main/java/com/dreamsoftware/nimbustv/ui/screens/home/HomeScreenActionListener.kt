package com.dreamsoftware.nimbustv.ui.screens.home

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener
import com.dreamsoftware.nimbustv.domain.model.ChannelBO
import com.dreamsoftware.nimbustv.domain.model.PlayListBO

interface HomeScreenActionListener: IFudgeTvScreenActionListener {

    fun onImportNewPlaylistClicked()
    fun onImportNewPlaylistCancelled()
    fun onImportNewPlayListConfirmed()
    fun onNewPlayListUrlUpdated(newValue: String)
    fun onNewPlayListAliasUpdated(newValue: String)
    fun onNewPlaylistSelected(newValue: PlayListBO)
    fun onChannelFocused(value: ChannelBO)
    fun onChannelPressed(value: ChannelBO)
    fun onNewCategorySelected(newValue: String)
    fun onManagePlaylistClicked()
}