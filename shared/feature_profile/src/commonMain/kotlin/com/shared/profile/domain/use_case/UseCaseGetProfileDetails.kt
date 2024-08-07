package com.shared.profile.domain.use_case

import com.shared.profile.domain.models.User
import com.shared.profile.domain.repo.ProfileRepo
import kotlinx.coroutines.flow.Flow

class UseCaseGetProfileDetails(
    val profileRepo: ProfileRepo
)  {
    fun getProfileDetails(): Flow<User> = profileRepo.getProfileDetails()
}