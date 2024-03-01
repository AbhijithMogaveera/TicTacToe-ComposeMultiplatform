package com.tictactao.profile.domain.use_case

import arrow.core.Eval
import arrow.core.Eval.Companion.always
import arrow.core.Eval.Companion.defer
import arrow.core.Eval.Companion.later
import arrow.core.Eval.Companion.now
import arrow.core.getOrElse
import com.abhijith.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.abhijith.foundation.usecase.SyncUseCase
import com.tictactao.profile.domain.repo.ProfileRepo
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class UseCaseSyncProfileDetailsWIthServer(
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