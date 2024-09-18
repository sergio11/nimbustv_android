package com.dreamsoftware.nimbustv.ui.screens.profiles.secure

import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface SecurePinScreenActionListener: IFudgeTvScreenActionListener {

    fun onUnlockPinChanged(unlockPin: String)
    fun onVerifyPressed()
    fun onCancelPressed()
}