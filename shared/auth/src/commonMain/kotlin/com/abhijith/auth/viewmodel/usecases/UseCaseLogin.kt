package com.abhijith.auth.viewmodel.usecases

import com.abhijith.auth.models.LoginResult

internal interface UseCaseLogin {
    suspend fun login(userName: String, password: String): LoginResult
}

