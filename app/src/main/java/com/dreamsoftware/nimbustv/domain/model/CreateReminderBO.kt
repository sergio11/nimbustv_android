package com.dreamsoftware.nimbustv.domain.model

data class CreateReminderBO(
    val id: String,
    val executionAtMillis: Long,
    val profileId: String,
    val scheduleId: String
)
