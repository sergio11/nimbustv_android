package com.dreamsoftware.nimbustv.domain.usecase

import com.dreamsoftware.fudge.core.FudgeTvUseCase
import com.dreamsoftware.nimbustv.domain.model.EpgDataBO
import com.dreamsoftware.nimbustv.domain.repository.IEpgRepository

class GetEpgDataUseCase(
    private val epgRepository: IEpgRepository
) : FudgeTvUseCase<List<EpgDataBO>>() {

    override suspend fun onExecuted(): List<EpgDataBO> =
        epgRepository.findAll()
}