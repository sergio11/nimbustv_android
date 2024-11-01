package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.domain.model.AvatarTypeEnum
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import com.dreamsoftware.nimbustv.utils.enumNameOfOrDefault

internal class ProfileMapper: IOneSideMapper<ProfileEntity, ProfileBO> {

    override fun mapInToOut(input: ProfileEntity): ProfileBO = with(input) {
        ProfileBO(
            id = id,
            alias = alias,
            avatarType = enumNameOfOrDefault(avatarType, AvatarTypeEnum.BOY)
        )
    }

    override fun mapInListToOutList(input: Iterable<ProfileEntity>): Iterable<ProfileBO> =
        input.map(::mapInToOut)
}