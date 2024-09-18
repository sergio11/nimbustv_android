package com.dreamsoftware.nimbustv.ui.screens.profiles.save

import com.dreamsoftware.nimbustv.domain.model.AvatarTypeEnum
import com.dreamsoftware.fudge.core.IFudgeTvScreenActionListener

interface SaveProfileScreenActionListener: IFudgeTvScreenActionListener {

    fun onAliasChanged(alias: String)
    fun onPinChanged(pin: String)
    fun onAvatarTypeChanged(avatarType: AvatarTypeEnum)
    fun onSaveProfilePressed()
    fun onAdvanceConfigurationPressed()
    fun onCancelPressed()
}