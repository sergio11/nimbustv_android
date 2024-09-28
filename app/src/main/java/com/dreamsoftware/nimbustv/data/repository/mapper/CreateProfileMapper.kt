package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper

internal class CreateProfileMapper: IOneSideMapper<CreateProfileRequestBO, ProfileEntity> {

    override fun mapInToOut(input: CreateProfileRequestBO): ProfileEntity = with(input) {
        ProfileEntity(
            alias = alias,
            avatarType = avatarType.name,
            securedPin = pin.toString()
        )
    }

    override fun mapInListToOutList(input: Iterable<CreateProfileRequestBO>): Iterable<ProfileEntity> =
        input.map(::mapInToOut)
}