package com.dreamsoftware.nimbustv.domain.repository

import com.dreamsoftware.nimbustv.domain.exception.ClearProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.DeleteProfileException
import com.dreamsoftware.nimbustv.domain.exception.FetchProfilesException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileByIdException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.SelectProfileException
import com.dreamsoftware.nimbustv.domain.exception.UpdateProfileException
import com.dreamsoftware.nimbustv.domain.exception.VerifyPinException
import com.dreamsoftware.nimbustv.domain.exception.CreateProfileException
import com.dreamsoftware.nimbustv.domain.exception.DomainRepositoryException
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO

interface IProfilesRepository {

    @Throws(FetchProfilesException::class)
    suspend fun getProfiles(): List<ProfileBO>

    @Throws(FetchProfilesException::class)
    suspend fun countProfiles(): Long

    @Throws(UpdateProfileException::class)
    suspend fun updateProfile(profileId: String, data: UpdatedProfileRequestBO): ProfileBO

    @Throws(DeleteProfileException::class)
    suspend fun deleteProfile(profileId: String): Boolean

    @Throws(CreateProfileException::class)
    suspend fun createProfile(data: CreateProfileRequestBO): ProfileBO

    @Throws(SelectProfileException::class)
    suspend fun selectProfile(profile: ProfileBO)

    @Throws(VerifyPinException::class)
    suspend fun verifyPin(profileId: String, pin: Int)

    @Throws(GetProfileByIdException::class)
    suspend fun getProfileById(profileId: String): ProfileBO

    @Throws(DomainRepositoryException::class)
    suspend fun hasProfileSelected(): Boolean

    @Throws(GetProfileSelectedException::class)
    suspend fun getProfileSelected(): ProfileBO

    @Throws(ClearProfileSelectedException::class)
    suspend fun clearProfileSelected()
}