package com.dreamsoftware.nimbustv.ui.screens.epgsources

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener
import com.dreamsoftware.nimbustv.domain.model.EpgBO

interface EpgSourcesScreenActionListener: IFudgeTvScreenActionListener {

    fun onImportNewEpgClicked()
    fun onImportNewEpgCancelled()
    fun onImportNewEpgConfirmed()
    fun onNewEpgUrlUpdated(newValue: String)
    fun onNewEpgAliasUpdated(newValue: String)
    fun onDeleteEpgClicked(epg: EpgBO)
    fun onDeleteEpgConfirmed()
    fun onDeleteEpgCancelled()
}