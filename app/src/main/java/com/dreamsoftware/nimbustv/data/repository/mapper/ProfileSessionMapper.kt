package com.dreamsoftware.nimbustv.data.repository.mapper

import com.dreamsoftware.nimbustv.data.preferences.dto.ProfileSelectedPreferenceDTO
import com.dreamsoftware.nimbustv.domain.model.AvatarTypeEnum
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.utils.IMapper
import com.dreamsoftware.nimbustv.utils.enumNameOfOrDefault

internal class ProfileSessionMapper: IMapper<ProfileBO, ProfileSelectedPreferenceDTO> {

    override fun mapInToOut(input: ProfileBO): ProfileSelectedPreferenceDTO = with(input) {
        ProfileSelectedPreferenceDTO(
            id = id,
            alias = alias,
            type = avatarType.name
        )
    }

    override fun mapInListToOutList(input: Iterable<ProfileBO>): Iterable<ProfileSelectedPreferenceDTO> =
        input.map(::mapInToOut)

    override fun mapOutToIn(input: ProfileSelectedPreferenceDTO): ProfileBO = with(input) {
        ProfileBO(
            id = id,
            alias = alias,
            avatarType = enumNameOfOrDefault(type, AvatarTypeEnum.BOY)
        )
    }

    override fun mapOutListToInList(input: Iterable<ProfileSelectedPreferenceDTO>): Iterable<ProfileBO> =
        input.map(::mapOutToIn)
}