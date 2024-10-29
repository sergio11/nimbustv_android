package com.dreamsoftware.nimbustv.domain.model

data class CreateReminderBO(
    val id: String,
    val profileId: String,
    val scheduleId: String
)
