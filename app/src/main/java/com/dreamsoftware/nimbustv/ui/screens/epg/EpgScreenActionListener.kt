package com.dreamsoftware.nimbustv.ui.screens.epg

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface EpgScreenActionListener: IFudgeTvScreenActionListener {

    fun onRemoveEpgData()
    fun onImportNewEpgData()
    fun onRemoveEpgDataConfirmed()
    fun onRemoveEpgDataCancelled()
}