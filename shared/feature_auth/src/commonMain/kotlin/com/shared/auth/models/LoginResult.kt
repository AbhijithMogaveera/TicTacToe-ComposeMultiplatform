package com.shared.auth.models

enum class LoginResult {
    LOGIN_SUCCESSFUL,
    INVALID_PASSWORD,
    INVALID_EMAIL_ID,
    CLIENT_SIDE_ERROR,
    SERVER_SIDE_ISSUE,
    UNKNOWN_ERROR,
}