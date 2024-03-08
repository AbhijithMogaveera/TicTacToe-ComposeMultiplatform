package com.abhijith.auth.viewmodel.usecases.impl

import arrow.core.Option
import com.abhijith.auth.util.UserAccountUtil
import com.abhijith.auth.viewmodel.usecases.UseCaseGetAuthToken
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal class UseCaseGetAuthTokenDefaultImpl(
    val authManger: UserAccountUtil
):UseCaseGetAuthToken {
    override fun getToken(): StateFlow<Option<String>> {
        return authManger.token
    }
}