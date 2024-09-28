package com.dreamsoftware.nimbustv.domain.model

import java.util.Date

data class PlayListBO(
    val id: Long,
    val alias: String,
    val creationDate: Date,
    val originalUrl: String,
    val profileId: Long
)