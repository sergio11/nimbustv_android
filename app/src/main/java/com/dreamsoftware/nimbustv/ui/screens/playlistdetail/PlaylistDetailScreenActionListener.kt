package com.dreamsoftware.nimbustv.ui.screens.playlistdetail

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener
import com.dreamsoftware.nimbustv.domain.model.ChannelBO

interface PlaylistDetailScreenActionListener: IFudgeTvScreenActionListener {

    fun onDeletePlaylistClicked()
    fun onDeletePlaylistConfirmed()
    fun onDeletePlaylistCancelled()
    fun onPlayChannel(channelBO: ChannelBO)
    fun onRemoveFromPlaylist(channelBO: ChannelBO)
    fun onCloseChannelDetail()
    fun onOpenChannelDetail(channel: ChannelBO)
}