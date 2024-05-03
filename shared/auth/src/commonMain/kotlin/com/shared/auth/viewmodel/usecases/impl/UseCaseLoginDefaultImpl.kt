package com.shared.auth.viewmodel.usecases.impl

import com.shared.auth.util.UserAccountUtil
import com.shared.auth.models.LoginResult
import com.shared.auth.viewmodel.usecases.UseCaseLogin

internal class UseCaseLoginDefaultImpl(
    private val userAccountUtil: UserAccountUtil
) : UseCaseLogin {

    companion object {
        private const val INVALID_USER_ID = "invalid_user_id"
        private const val USER_NAME_REQUIRED = "user_name_required"
        private const val INVALID_PASSWORD = "invalid_password"
    }

    override suspend fun login(
        userName: String, password: String
    ): LoginResult {
        userAccountUtil.login(
            userName, password
        ).onLeft {
            it.isClientSideError { clientSideError ->
                if (
                    clientSideError.issue.key == INVALID_USER_ID ||
                    clientSideError.issue.key == USER_NAME_REQUIRED
                ) {
                    return LoginResult.INVALID_EMAIL_ID
                }
                if (clientSideError.issue.key == INVALID_PASSWORD) {
                    return LoginResult.INVALID_PASSWORD
                }
                return LoginResult.CLIENT_SIDE_ERROR
            }.isServerSideError { _ ->
                return LoginResult.SERVER_SIDE_ISSUE
            }.isUnKnownError {
                printStackTrace()
            }
        }
        return LoginResult.LoginSuccessful
    }

}