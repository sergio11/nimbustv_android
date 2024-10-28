package com.dreamsoftware.nimbustv.ui.screens.epg

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener
import com.dreamsoftware.nimbustv.domain.model.EpgBO
import com.dreamsoftware.nimbustv.ui.screens.epg.model.ScheduleVO

interface EpgScreenActionListener: IFudgeTvScreenActionListener {

    fun onNewEpgSelected(newValue: EpgBO)
    fun onManageEpgListClicked()
    fun onImportNewEpgData()
    fun onImportNewEpgDataConfirmed()
    fun onImportNewEpgDataCancelled()
    fun onNewEpgAliasUpdated(newValue: String)
    fun onNewEpgUrlUpdated(newValue: String)
    fun onOpenEpgChannel(channelId: String)
    fun onOpenScheduleDetail(schedule: ScheduleVO)
    fun onCloseScheduleDetail()
}