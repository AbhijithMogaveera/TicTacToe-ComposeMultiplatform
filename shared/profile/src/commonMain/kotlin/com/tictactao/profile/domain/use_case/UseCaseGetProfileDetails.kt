package com.tictactao.profile.domain.use_case

import com.abhijith.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.tictactao.profile.domain.models.User
import com.tictactao.profile.domain.repo.ProfileRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.launch

class UseCaseGetProfileDetails(
    val profileRepo: ProfileRepo,
    val useCaseGetAuthToken: UseCaseGetAuthToken,
    val appScope:CoroutineScope
){
    init {
        appScope.launch {
            useCaseGetAuthToken.getToken().collectLatest {
                it.onSome {
                    profileRepo.syncProfileDetailsWithServer()
                }
            }
        }
    }
    fun getProfileDetails(): Flow<User> = profileRepo.getProfileDetails()
}