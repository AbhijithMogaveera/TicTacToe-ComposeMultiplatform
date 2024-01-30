package com.abhijith.auth.viewmodel.usecases

import com.abhijith.auth.AuthManager
import io.ktor.utils.io.printStack

interface LoginUseCase {
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

class LoginUseCaseDefaultImpl(
    private val authManager: AuthManager
) : LoginUseCase {

    companion object {
        private const val INVALID_USER_ID = "invalid_user_id"
        private const val INVALID_PASSWORD = "invalid_password"
    }

    override suspend fun login(
        userName: String, password: String
    ): LoginUseCase.Result {
        try {
            authManager.login(
                userName, password
            ).onLeft {
                it.isClientSideError { clientSideError ->
                    if (clientSideError.issue.key == INVALID_USER_ID) {
                        return LoginUseCase.Result.INVALID_EMAIL_ID
                    }
                    if (clientSideError.issue.key == INVALID_PASSWORD) {
                        return LoginUseCase.Result.INVALID_PASSWORD
                    }
                    return LoginUseCase.Result.CLIENT_SIDE_ERROR
                }.isServerSideError { _ ->
                    return LoginUseCase.Result.SERVER_SIDE_ISSUE
                }.onException {
                    printStack()
                }
            }
        } catch (e: Exception) {
            e.printStack()
        }
        return LoginUseCase.Result.UNKNOWN_ERROR
    }

}