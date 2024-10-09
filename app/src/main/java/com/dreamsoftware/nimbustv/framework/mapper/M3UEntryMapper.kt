package com.dreamsoftware.nimbustv.framework.mapper


import com.dreamsoftware.nimbustv.domain.model.M3uEntryBO
import com.dreamsoftware.nimbustv.domain.model.StreamTypeEnum
import com.dreamsoftware.nimbustv.framework.m3u.model.M3uEntry
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import com.dreamsoftware.nimbustv.utils.enumNameOfOrDefault

internal class M3UEntryMapper: IOneSideMapper<M3uEntry, M3uEntryBO> {

    override fun mapInToOut(input: M3uEntry): M3uEntryBO = with(input) {
        M3uEntryBO(
            title = title,
            url = location.url.toExternalForm(),
            icon = metadata.logo,
            category = metadata["group-title"]?.replace(";", " "),
            manifestType = kodiProps["inputstream.adaptive.manifest_type"],
            licenseType = kodiProps["inputstream.adaptive.license_type"],
            licenseKey = kodiProps["inputstream.adaptive.license_key"],
            streamTypeEnum = enumNameOfOrDefault(streamType.name, StreamTypeEnum.VIDEO)
        )
    }

    override fun mapInListToOutList(input: Iterable<M3uEntry>): Iterable<M3uEntryBO> =
        input.map(::mapInToOut)
}