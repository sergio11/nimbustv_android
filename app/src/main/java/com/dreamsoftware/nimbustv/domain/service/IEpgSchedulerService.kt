package com.dreamsoftware.nimbustv.domain.service

interface IEpgSchedulerService {

    fun scheduleSyncEpgWorkForProfile(url: String, profileId: String)

    fun cancelSyncEpgWork(profileId: String)
}