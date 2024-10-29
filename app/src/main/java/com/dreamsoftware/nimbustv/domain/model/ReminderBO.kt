package com.dreamsoftware.nimbustv.domain.model

data class ReminderBO(
    val id: String,
    val executionAtMillis: Long,
    val scheduleId: String
)
