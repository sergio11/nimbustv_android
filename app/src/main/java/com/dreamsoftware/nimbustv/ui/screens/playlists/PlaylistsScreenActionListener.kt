package com.dreamsoftware.nimbustv.ui.screens.playlists

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener
import com.dreamsoftware.nimbustv.domain.model.PlayListBO

interface PlaylistsScreenActionListener: IFudgeTvScreenActionListener {

    fun onImportNewPlaylistClicked()
    fun onImportNewPlaylistCancelled()
    fun onImportNewPlayListConfirmed()
    fun onNewPlayListUrlUpdated(newValue: String)
    fun onNewPlayListAliasUpdated(newValue: String)
    fun onOpenPlaylistDetailClicked(playListBO: PlayListBO)
}