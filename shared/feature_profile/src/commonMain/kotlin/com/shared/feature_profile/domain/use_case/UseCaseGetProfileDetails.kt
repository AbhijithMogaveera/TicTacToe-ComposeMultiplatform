package com.shared.feature_profile.domain.use_case

import com.shared.feature_profile.domain.models.User
import com.shared.feature_profile.domain.repo.ProfileRepo
import kotlinx.coroutines.flow.Flow

class UseCaseGetProfileDetails(
    val profileRepo: ProfileRepo
)  {
    fun getProfileDetails(): Flow<User> = profileRepo.getProfileDetails()
}