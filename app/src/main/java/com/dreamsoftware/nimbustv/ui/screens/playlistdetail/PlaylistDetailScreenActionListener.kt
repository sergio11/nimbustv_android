package com.dreamsoftware.nimbustv.ui.screens.playlistdetail

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface PlaylistDetailScreenActionListener: IFudgeTvScreenActionListener {

    fun onDeletePlaylistClicked()
    fun onDeletePlaylistConfirmed()
    fun onDeletePlaylistCancelled()
}