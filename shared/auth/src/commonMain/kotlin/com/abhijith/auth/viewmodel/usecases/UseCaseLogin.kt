package com.abhijith.auth.viewmodel.usecases

interface UseCaseLogin {
    enum class Result {
        SUCCESS,
        INVALID_PASSWORD,
        INVALID_EMAIL_ID,
        CLIENT_SIDE_ERROR,
        SERVER_SIDE_ISSUE,
        UNKNOWN_ERROR
    }

    suspend fun login(userName: String, password: String): Result
}

