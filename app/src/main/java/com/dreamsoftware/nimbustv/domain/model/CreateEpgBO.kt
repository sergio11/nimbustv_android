package com.dreamsoftware.nimbustv.domain.model

data class CreateEpgBO(
    val id: String,
    val alias: String,
    val originalUrl: String,
    val profileId: String,
    val channelList: List<CreateEpgChannelBO>
)