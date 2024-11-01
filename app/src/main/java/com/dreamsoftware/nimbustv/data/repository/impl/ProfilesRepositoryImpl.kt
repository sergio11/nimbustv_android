package com.dreamsoftware.nimbustv.data.repository.impl

import com.dreamsoftware.nimbustv.data.database.datasource.IProfileLocalDataSource
import com.dreamsoftware.nimbustv.data.database.entity.ProfileEntity
import com.dreamsoftware.nimbustv.data.database.exception.DatabaseException
import com.dreamsoftware.nimbustv.data.preferences.datasource.IProfileSessionDataSource
import com.dreamsoftware.nimbustv.data.preferences.exception.ClearProfileSelectedPreferenceLocalException
import com.dreamsoftware.nimbustv.data.preferences.exception.FetchProfileSelectedPreferenceLocalException
import com.dreamsoftware.nimbustv.data.preferences.exception.SaveProfileSelectedPreferenceLocalException
import com.dreamsoftware.nimbustv.data.repository.impl.core.SupportRepositoryImpl
import com.dreamsoftware.nimbustv.data.repository.mapper.UpdateProfileData
import com.dreamsoftware.nimbustv.domain.exception.ClearProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.CreateProfileException
import com.dreamsoftware.nimbustv.domain.exception.DeleteProfileException
import com.dreamsoftware.nimbustv.domain.exception.DomainRepositoryException
import com.dreamsoftware.nimbustv.domain.exception.FetchProfilesException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileByIdException
import com.dreamsoftware.nimbustv.domain.exception.GetProfileSelectedException
import com.dreamsoftware.nimbustv.domain.exception.SelectProfileException
import com.dreamsoftware.nimbustv.domain.exception.UpdateProfileException
import com.dreamsoftware.nimbustv.domain.exception.VerifyPinException
import com.dreamsoftware.nimbustv.domain.model.CreateProfileRequestBO
import com.dreamsoftware.nimbustv.domain.model.ProfileBO
import com.dreamsoftware.nimbustv.domain.model.UpdatedProfileRequestBO
import com.dreamsoftware.nimbustv.domain.repository.IProfilesRepository
import com.dreamsoftware.nimbustv.utils.IOneSideMapper
import kotlinx.coroutines.CoroutineDispatcher

internal class ProfilesRepositoryImpl(
    private val profileLocalDataSource: IProfileLocalDataSource,
    private val profilesMapper: IOneSideMapper<ProfileEntity, ProfileBO>,
    private val createProfileMapper: IOneSideMapper<CreateProfileRequestBO, ProfileEntity>,
    private val updateProfileMapper: IOneSideMapper<UpdateProfileData, ProfileEntity>,
    private val profileSessionDataSource: IProfileSessionDataSource,
    dispatcher: CoroutineDispatcher
) : SupportRepositoryImpl(dispatcher), IProfilesRepository {

    @Throws(FetchProfilesException::class)
    override suspend fun getProfiles(): List<ProfileBO> = safeExecute {
        try {
            profileLocalDataSource
                .findAll()
                .let(profilesMapper::mapInListToOutList)
                .toList()
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw FetchProfilesException("An error occurred when fetching profiles", ex)
        }
    }

    @Throws(FetchProfilesException::class)
    override suspend fun countProfiles(): Long = safeExecute {
        try {
            profileLocalDataSource.count()
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw FetchProfilesException("An error occurred when fetching profiles", ex)
        }
    }

    @Throws(UpdateProfileException::class)
    override suspend fun updateProfile(
        profileId: String,
        data: UpdatedProfileRequestBO
    ): ProfileBO = safeExecute {
        try {
            with(profileLocalDataSource) {
                val currentProfile = findById(profileId)
                update(
                    updateProfileMapper.mapInToOut(
                        UpdateProfileData(
                            currentProfile = currentProfile,
                            updatedProfileRequest = data
                        )
                    )
                )
                    .let(profilesMapper::mapInToOut)
            }
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw UpdateProfileException("An error occurred when updating profile", ex)
        }
    }

    @Throws(DeleteProfileException::class)
    override suspend fun deleteProfile(profileId: String): Boolean = safeExecute {
        try {
            with(profileLocalDataSource) {
                delete(profileId) > 0
            }
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw DeleteProfileException("An error occurred when deleting profile", ex)
        }
    }

    @Throws(CreateProfileException::class)
    override suspend fun createProfile(data: CreateProfileRequestBO): ProfileBO = safeExecute {
        try {
            profileLocalDataSource
                .insert(createProfileMapper.mapInToOut(data))
                .let(profilesMapper::mapInToOut)
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw CreateProfileException("An error occurred when creating profiles", ex)
        }
    }

    @Throws(SelectProfileException::class)
    override suspend fun selectProfile(profile: ProfileBO): Unit = safeExecute {
        try {
            profileSessionDataSource.saveProfileSelectedId(profile.id)
        } catch (ex: SaveProfileSelectedPreferenceLocalException) {
            ex.printStackTrace()
            throw SelectProfileException(
                "An error occurred when saving the current profile selected",
                ex
            )
        }
    }

    @Throws(VerifyPinException::class)
    override suspend fun verifyPin(profileId: String, pin: Int): Unit = safeExecute {
        try {
            profileLocalDataSource
                .verifyPin(profileId, pin).let { isSuccess ->
                    if (!isSuccess) {
                        throw VerifyPinException("Pin verification failed for profile $profileId")
                    }
                }
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw VerifyPinException("An error occurred when verifying pin profile", ex)
        }
    }

    @Throws(GetProfileByIdException::class)
    override suspend fun getProfileById(profileId: String): ProfileBO = safeExecute {
        try {
            profileLocalDataSource
                .findById(profileId)
                .let(profilesMapper::mapInToOut)
        } catch (ex: DatabaseException) {
            ex.printStackTrace()
            throw GetProfileByIdException("An error occurred when getting profile by id", ex)
        }
    }

    @Throws(DomainRepositoryException::class)
    override suspend fun hasProfileSelected(): Boolean =
        try {
            profileSessionDataSource.getProfileSelectedId().isNotBlank()
        } catch (ex: FetchProfileSelectedPreferenceLocalException) {
            false
        }

    @Throws(GetProfileSelectedException::class)
    override suspend fun getProfileSelected(): ProfileBO = safeExecute {
        try {
            profileLocalDataSource
                .findById(profileSessionDataSource.getProfileSelectedId())
                .let(profilesMapper::mapInToOut)
        } catch (ex: DatabaseException) {
            throw GetProfileByIdException("An error occurred when getting profile by id", ex)
        } catch (ex: FetchProfileSelectedPreferenceLocalException) {
            throw GetProfileByIdException("An error occurred when getting profile by id", ex)
        }
    }

    @Throws(ClearProfileSelectedException::class)
    override suspend fun clearProfileSelected() = safeExecute {
        try {
            profileSessionDataSource.clearProfileSelectedId()
        } catch (ex: ClearProfileSelectedPreferenceLocalException) {
            throw ClearProfileSelectedException(
                "An error occurred when trying to clear the profile selected",
                ex
            )
        }
    }
}