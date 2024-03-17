package com.abhijith.auth.models

enum class RegistrationResult {
    SUCCESS,
    INVALID_EMAIL_ID,
    CLIENT_SIDE_ERROR,
    SERVER_SIDE_ISSUE,
    UNKNOWN_ERROR,
    USER_ALREADY_EXISTS,
    INVALID_PASSWORD
}