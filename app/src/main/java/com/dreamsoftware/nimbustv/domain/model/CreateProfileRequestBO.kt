package com.dreamsoftware.nimbustv.domain.model

data class CreateProfileRequestBO(
    val alias: String,
    val pin: String,
    val avatarType: AvatarTypeEnum
) {
    companion object {
        const val FIELD_ALIAS = "alias"
        const val FIELD_PIN = "pin"
    }
}
