package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.CreateReminderException
import com.dreamsoftware.nimbustv.domain.exception.FetchRemindersByProfileException
import com.dreamsoftware.nimbustv.domain.model.CreateReminderBO
import com.dreamsoftware.nimbustv.domain.model.ReminderBO

interface IRemindersRepository {

    @Throws(FetchRemindersByProfileException::class)
    suspend fun findAllByProfileId(profileId: String): List<ReminderBO>

    @Throws(CreateReminderException::class)
    suspend fun create(data: CreateReminderBO): ReminderBO
}