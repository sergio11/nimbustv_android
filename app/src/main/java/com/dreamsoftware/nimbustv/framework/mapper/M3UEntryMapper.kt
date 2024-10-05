package com.dreamsoftware.nimbustv.framework.mapper

import com.dreamsoftware.nimbustv.domain.model.M3uEntryBO
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import net.bjoernpetersen.m3u.model.M3uEntry

internal class M3UEntryMapper: IOneSideMapper<M3uEntry, M3uEntryBO> {

    override fun mapInToOut(input: M3uEntry): M3uEntryBO = with(input) {
        M3uEntryBO(
            title = title,
            url = location.url.toExternalForm(),
            icon = metadata.logo,
            category = metadata["group-title"]
        )
    }

    override fun mapInListToOutList(input: Iterable<M3uEntry>): Iterable<M3uEntryBO> =
        input.map(::mapInToOut)
}