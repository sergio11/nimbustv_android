package com.dreamsoftware.nimbustv.domain.service

interface IReminderSchedulerService {

    suspend fun scheduleReminder(reminderId: String)

    suspend fun cancelReminder(reminderId: String)
}