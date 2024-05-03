package com.shared.profile.domain.use_case

import com.shared.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.shared.compose_foundation.usecase.SyncUseCase
import com.shared.profile.domain.repo.ProfileRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

internal class UseCaseSyncProfileDetailsWIthServer(
    val useCaseGetAuthToken: UseCaseGetAuthToken,
    val appScope: CoroutineScope,
    val profileRepo: ProfileRepo
) : SyncUseCase {
    override fun startSync() {
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

    override fun stopSyncs() {

    }

}