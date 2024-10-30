package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.CreateReminderException
import com.dreamsoftware.nimbustv.domain.exception.DeleteReminderByIdException
import com.dreamsoftware.nimbustv.domain.exception.FetchReminderByIdException
import com.dreamsoftware.nimbustv.domain.exception.FetchReminderByScheduleIdException
import com.dreamsoftware.nimbustv.domain.exception.FetchRemindersByProfileException
import com.dreamsoftware.nimbustv.domain.exception.VerifyReminderByIdException
import com.dreamsoftware.nimbustv.domain.model.CreateReminderBO
import com.dreamsoftware.nimbustv.domain.model.ReminderBO

interface IRemindersRepository {

    @Throws(FetchReminderByIdException::class)
    suspend fun findById(id: String): ReminderBO

    @Throws(FetchReminderByScheduleIdException::class)
    suspend fun findByScheduleId(scheduleId: String): ReminderBO

    @Throws(FetchRemindersByProfileException::class)
    suspend fun findAllByProfileId(profileId: String): List<ReminderBO>

    @Throws(CreateReminderException::class)
    suspend fun create(data: CreateReminderBO): ReminderBO

    @Throws(DeleteReminderByIdException::class)
    suspend fun deleteById(id: String)

    @Throws(VerifyReminderByIdException::class)
    suspend fun hasReminder(scheduleId: String): Boolean
}