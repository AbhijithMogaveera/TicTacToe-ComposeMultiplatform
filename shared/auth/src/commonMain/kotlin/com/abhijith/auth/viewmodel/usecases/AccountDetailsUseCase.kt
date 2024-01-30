package com.abhijith.auth.viewmodel.usecases

import com.abhijith.auth.AuthManager
import com.abhijith.auth.UserState
import com.abhijith.auth.apis.JwtToken
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

interface AccountDetailsUseCase {
    sealed class Response {
        data object NoLogin : Response()
        data class LoggedInUser(
            val userName: String,
            val token: JwtToken
        ) : Response()
    }

    fun getUserLoginActivity(): Flow<Response>
}

class AccountDetailsUseCaseDefaultImpl(
    val authManager: AuthManager
) : AccountDetailsUseCase {
    override fun getUserLoginActivity(): Flow<AccountDetailsUseCase.Response> {
        return authManager.loggedInUser.map {
            when (it) {
                is UserState.Found -> AccountDetailsUseCase.Response.LoggedInUser(
                    userName = it.user.userName,
                    token = it.user.token
                )

                UserState.Loading -> AccountDetailsUseCase.Response.NoLogin
                UserState.UserNotFound -> AccountDetailsUseCase.Response.NoLogin
            }
        }
    }

}