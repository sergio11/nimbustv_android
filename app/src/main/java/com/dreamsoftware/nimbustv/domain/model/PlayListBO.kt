package com.dreamsoftware.nimbustv.domain.model

import java.util.Date

data class PlayListBO(
    val id: String,
    val alias: String,
    val creationDate: Date,
    val originalUrl: String,
    val profileId: String,
    val channelsCount: Long
)