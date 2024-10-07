package com.dreamsoftware.nimbustv.domain.model

data class ChannelBO(
    val id: String,
    val title: String?,
    val url: String,
    val icon: String?,
    val category: String?
)