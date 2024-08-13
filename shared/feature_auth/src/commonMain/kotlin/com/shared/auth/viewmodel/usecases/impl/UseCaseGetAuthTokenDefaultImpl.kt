package com.shared.auth.viewmodel.usecases.impl

import arrow.core.Option
import com.shared.auth.util.AuthManager
import com.shared.auth.viewmodel.usecases.UseCaseGetAuthToken
import kotlinx.coroutines.flow.StateFlow

internal class UseCaseGetAuthTokenDefaultImpl(
    val authManger: AuthManager
):UseCaseGetAuthToken {
    override fun getToken(): StateFlow<Option<String>> {
        return authManger.token
    }
}