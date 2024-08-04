package com.shared.auth.viewmodel.usecases

import com.shared.auth.models.LoginResult

internal interface UseCaseLogin {
    suspend fun login(userName: String, password: String): LoginResult
}

