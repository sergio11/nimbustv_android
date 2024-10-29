package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ReminderEntity
import com.dreamsoftware.nimbustv.domain.model.CreateReminderBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class CreateRemindersMapper: IOneSideMapper<CreateReminderBO, ReminderEntity> {

    override fun mapInToOut(input: CreateReminderBO): ReminderEntity = with(input) {
        ReminderEntity(
            id = id,
            executionAtMillis = executionAtMillis,
            profileId = profileId,
            scheduleId = scheduleId
        )
    }

    override fun mapInListToOutList(input: Iterable<CreateReminderBO>): Iterable<ReminderEntity> =
        input.map(::mapInToOut)
}