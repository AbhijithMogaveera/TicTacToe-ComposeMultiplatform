package com.shared.feature_profile.domain.use_case

import com.shared.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.shared.compose_foundation.StartUpTask
import com.shared.feature_profile.domain.repo.ProfileRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UseCaseSyncProfileDetailsWIthServer(
    val useCaseGetAuthToken: UseCaseGetAuthToken,
    val appScope: CoroutineScope,
    val profileRepo: ProfileRepo
) : StartUpTask {
    private fun startSync() {
        appScope.launch {
            useCaseGetAuthToken.getToken().collectLatest { token ->
                token.onSome {
                    profileRepo.syncProfileDetailsWithServer().onLeft {
                        it.exception.printStackTrace()
                    }
                }
                token.onNone {
                    profileRepo.clearProfileData()
                }
            }
        }
    }

    override fun execute() {
        startSync()
    }
}