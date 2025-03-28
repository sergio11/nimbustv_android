package com.dreamsoftware.nimbustv.ui.screens.app

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface IAppScreenActionListener: IFudgeTvScreenActionListener {
    fun onOpenSettingsPressed()
    fun onRestartAppPressed()
    fun onScheduleReminderAccepted()
}