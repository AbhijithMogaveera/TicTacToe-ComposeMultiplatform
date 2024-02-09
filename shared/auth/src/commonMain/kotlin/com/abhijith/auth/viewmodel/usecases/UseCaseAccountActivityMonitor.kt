package com.abhijith.auth.viewmodel.usecases

import com.abhijith.auth.apis.JwtToken
import kotlinx.coroutines.flow.Flow

interface UseCaseAccountActivityMonitor {
    sealed class Response {
        data object NoLogin : Response()
        data class LoggedInUser(
            val userName: String,
            val token: JwtToken
        ) : Response()
    }

    fun getUserLoginActivity(): Flow<Response>
}

