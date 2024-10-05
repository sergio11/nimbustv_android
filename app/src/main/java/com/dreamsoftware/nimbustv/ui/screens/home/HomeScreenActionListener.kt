package com.dreamsoftware.nimbustv.ui.screens.home

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface HomeScreenActionListener: IFudgeTvScreenActionListener {

    fun onImportNewPlaylistClicked()
    fun onImportNewPlaylistCancelled()
    fun onNewPlayListUrlUpdated(newValue: String)
}