package com.abhijith.auth.viewmodel.usecases.impl

import com.abhijith.auth.util.UserAccountUtil
import com.abhijith.auth.viewmodel.usecases.UseCaseRegistration

class UseCaseRegistrationDefaultImpl(
    private val userAccountUtil: UserAccountUtil
) : UseCaseRegistration {

    companion object {
        private const val INVALID_USER_ID = "invalid_user_id"
        private const val USER_ALREADY_EXISTS = "user_already_exists"
        private const val INVALID_PASSWORD = "invalid_password"
    }

    override suspend fun register(
        userName: String,
        password: String
    ): UseCaseRegistration.Result {
        userAccountUtil.register(userName, password).onLeft {
            it.exception.printStackTrace()
            it.isClientSideError { clientSideError ->
                if (clientSideError.issue.key == USER_ALREADY_EXISTS) {
                    return UseCaseRegistration.Result.USER_ALREADY_EXISTS
                }
                if (clientSideError.issue.key == INVALID_USER_ID) {
                    return UseCaseRegistration.Result.INVALID_EMAIL_ID
                }
                if(clientSideError.issue.key == INVALID_PASSWORD){
                    return UseCaseRegistration.Result.INVALID_PASSWORD
                }
            }.isServerSideError {
                return UseCaseRegistration.Result.SERVER_SIDE_ISSUE
            }.isUnKnownError {
                printStackTrace()
            }
        }.onRight {

            return UseCaseRegistration.Result.SUCCESS
        }
        return UseCaseRegistration.Result.UNKNOWN_ERROR

    }
}