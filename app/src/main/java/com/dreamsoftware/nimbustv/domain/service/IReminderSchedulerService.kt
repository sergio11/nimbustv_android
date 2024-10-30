package com.dreamsoftware.nimbustv.domain.service

import com.dreamsoftware.nimbustv.domain.model.ReminderBO

interface IReminderSchedulerService {

    suspend fun scheduleReminder(reminder: ReminderBO)

    suspend fun cancelReminder(reminderId: String)
}