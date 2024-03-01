package com.abhijith.auth.viewmodel.usecases

interface UseCaseRegistration {
    enum class Result {
        SUCCESS,
        INVALID_EMAIL_ID,
        CLIENT_SIDE_ERROR,
        SERVER_SIDE_ISSUE,
        UNKNOWN_ERROR,
        USER_ALREADY_EXISTS,
        INVALID_PASSWORD
    }
    suspend fun register(userName:String, password:String):Result
}