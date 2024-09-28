package com.dreamsoftware.nimbustv.domain.model

data class CreateProfileRequestBO(
    val alias: String,
    val pin: Int,
    val avatarType: AvatarTypeEnum
) {
    companion object {
        const val FIELD_ALIAS = "alias"
        const val FIELD_PIN = "pin"
    }
}
