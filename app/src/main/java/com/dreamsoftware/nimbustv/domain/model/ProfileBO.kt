package com.dreamsoftware.nimbustv.domain.model

data class ProfileBO(
    val id: String,
    val alias: String,
    val avatarType: AvatarTypeEnum
)