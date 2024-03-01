package com.abhijith.auth.viewmodel.usecases.impl

import com.abhijith.auth.util.UserAccountUtil
import com.abhijith.auth.viewmodel.usecases.UseCaseLogin

class UseCaseLoginDefaultImpl(
    private val userAccountUtil: UserAccountUtil
) : UseCaseLogin {

    companion object {
        private const val INVALID_USER_ID = "invalid_user_id"
        private const val USER_NAME_REQUIRED = "user_name_required"
        private const val INVALID_PASSWORD = "invalid_password"
    }

    override suspend fun login(
        userName: String, password: String
    ): UseCaseLogin.Result {
        userAccountUtil.login(
            userName, password
        ).onLeft {
            it.isClientSideError { clientSideError ->
                if (
                    clientSideError.issue.key == INVALID_USER_ID ||
                    clientSideError.issue.key == USER_NAME_REQUIRED
                ) {
                    return UseCaseLogin.Result.INVALID_EMAIL_ID
                }
                if (clientSideError.issue.key == INVALID_PASSWORD) {
                    return UseCaseLogin.Result.INVALID_PASSWORD
                }
                return UseCaseLogin.Result.CLIENT_SIDE_ERROR
            }.isServerSideError { _ ->
                return UseCaseLogin.Result.SERVER_SIDE_ISSUE
            }.isUnKnownError {
                printStackTrace()
            }
        }
        return UseCaseLogin.Result.LoginSuccessful
    }

}