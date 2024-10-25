package com.dreamsoftware.nimbustv.ui.screens.epg

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface EpgScreenActionListener: IFudgeTvScreenActionListener {

    fun onRemoveEpgData()
    fun onImportNewEpgData()
    fun onImportNewEpgDataConfirmed()
    fun onImportNewEpgDataCancelled()
    fun onRemoveEpgDataConfirmed()
    fun onRemoveEpgDataCancelled()
    fun onNewEpgDataUrlUpdated(newValue: String)
    fun onOpenEpgChannel(channelId: String)
}