package com.dreamsoftware.nimbustv.domain.model

data class M3uEntryBO(
    val title: String?,
    val url: String,
    val icon: String?,
    val category: String?,
    val manifestType: String?,
    val licenseType: String?,
    val licenseKey: String?,
    val streamTypeEnum: StreamTypeEnum
)
