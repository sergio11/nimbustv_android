package com.dreamsoftware.nimbustv.domain.model

data class UpdateEpgBO(
    val id: String,
    val channelList: List<CreateEpgChannelBO>
)