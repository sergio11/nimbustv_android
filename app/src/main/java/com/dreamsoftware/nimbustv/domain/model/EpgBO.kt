package com.dreamsoftware.nimbustv.domain.model

import java.util.Date

data class EpgBO(
    val id: String,
    val alias: String,
    val url: String,
    val creationDate: Date,
    val lastUpdatedDate: Date,
    val channelsCount: Long
)