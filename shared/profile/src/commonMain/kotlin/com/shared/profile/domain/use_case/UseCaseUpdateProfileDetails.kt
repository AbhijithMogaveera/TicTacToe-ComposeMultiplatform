package com.shared.profile.domain.use_case

import arrow.core.Either
import arrow.core.some
import com.shared.compose_foundation.module_config.SharedWithOtherModule
import com.darkrockstudios.libraries.mpfilepicker.MPFile
import com.shared.profile.domain.repo.ProfileRepo
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlin.time.Duration.Companion.seconds

@SharedWithOtherModule
class UseCaseUpdateProfileDetails(
    val profileRepo: ProfileRepo,
) {
    enum class UpdateState {
        Ideal, InProgress, Failed, Success
    }

    fun profileImage(it: MPFile<Any>): Flow<UpdateState> = flow {
        emit(UpdateState.InProgress)
        delay(3.seconds)
        when (val requestFailureUnitEither = profileRepo.updateProfileDetails(profileImage = it.some())) {
            is Either.Left -> {
                requestFailureUnitEither.value.exception.printStackTrace()
                emit(UpdateState.Failed)
            }

            is Either.Right -> {
                emit(UpdateState.Success)
            }
        }
    }

    suspend fun updateBio(bio: String) {
        profileRepo.updateProfileDetails(bio = bio.some())
    }
}