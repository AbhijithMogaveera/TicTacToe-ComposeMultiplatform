package com.shared.feature_profile.domain.repo

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import com.shared.compose_foundation.ktor.exceptions.RequestFailure
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import com.shared.feature_profile.domain.models.User
import kotlinx.coroutines.flow.Flow

interface ProfileRepo {
    fun getProfileDetails(): Flow<User>
    suspend fun syncProfileDetailsWithServer(): Either<RequestFailure, Unit>
    suspend fun updateProfileDetails(
        bio: Option<String> = None,
        profileImage: Option<MPFile<Any>> = None
    ): Either<RequestFailure, Unit>
    fun clearProfileData()
}