package com.dreamsoftware.nimbustv.ui.screens.profiles.delete

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface DeleteProfileScreenActionListener: IFudgeTvScreenActionListener {

    fun onDeletePressed()
    fun onCancelPressed()
    fun onProfileDeletedDialogClosed()
}