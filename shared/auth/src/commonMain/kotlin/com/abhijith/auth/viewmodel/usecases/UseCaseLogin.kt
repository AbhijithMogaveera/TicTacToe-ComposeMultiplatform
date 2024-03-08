package com.abhijith.auth.viewmodel.usecases
enum class LoginResult {
    LoginSuccessful,
    INVALID_PASSWORD,
    INVALID_EMAIL_ID,
    CLIENT_SIDE_ERROR,
    SERVER_SIDE_ISSUE,
    UNKNOWN_ERROR
}
internal interface UseCaseLogin {
    suspend fun login(userName: String, password: String): LoginResult
}

