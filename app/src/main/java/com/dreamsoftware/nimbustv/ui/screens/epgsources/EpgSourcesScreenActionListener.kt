package com.dreamsoftware.nimbustv.ui.screens.epgsources

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface EpgSourcesScreenActionListener: IFudgeTvScreenActionListener {

    fun onImportNewEpgClicked()
    fun onImportNewEpgCancelled()
    fun onImportNewEpgConfirmed()
    fun onNewEpgUrlUpdated(newValue: String)
    fun onNewEpgAliasUpdated(newValue: String)
}