package com.abhijith.auth.viewmodel.usecases.impl

import com.abhijith.auth.util.UserAccountUtil
import com.abhijith.auth.viewmodel.usecases.UseCaseLogin
import io.ktor.utils.io.printStack

class UseCaseLoginDefaultImpl(
    private val userAccountUtil: UserAccountUtil
) : UseCaseLogin {

    companion object {
        private const val INVALID_USER_ID = "invalid_user_id"
        private const val INVALID_PASSWORD = "invalid_password"
    }

    override suspend fun login(
        userName: String, password: String
    ): UseCaseLogin.Result {
        userAccountUtil.login(
            userName, password
        ).onLeft {
            it.isClientSideError { clientSideError ->
                if (clientSideError.issue.key == INVALID_USER_ID) {
                    return UseCaseLogin.Result.INVALID_EMAIL_ID
                }
                if (clientSideError.issue.key == INVALID_PASSWORD) {
                    return UseCaseLogin.Result.INVALID_PASSWORD
                }
                return UseCaseLogin.Result.CLIENT_SIDE_ERROR
            }.isServerSideError { _ ->
                return UseCaseLogin.Result.SERVER_SIDE_ISSUE
            }.isUnKnownError {
                printStack()
            }
        }
        return UseCaseLogin.Result.UNKNOWN_ERROR
    }

}