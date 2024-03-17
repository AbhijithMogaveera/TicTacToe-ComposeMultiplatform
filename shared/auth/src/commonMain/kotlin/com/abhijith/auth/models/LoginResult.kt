package com.abhijith.auth.models

public enum class LoginResult {
    LoginSuccessful,
    INVALID_PASSWORD,
    INVALID_EMAIL_ID,
    CLIENT_SIDE_ERROR,
    SERVER_SIDE_ISSUE,
    UNKNOWN_ERROR
}