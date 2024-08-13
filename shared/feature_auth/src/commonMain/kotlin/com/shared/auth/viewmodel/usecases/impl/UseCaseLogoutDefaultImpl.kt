package com.shared.auth.viewmodel.usecases.impl

import com.shared.auth.util.AuthManager
import com.shared.auth.viewmodel.usecases.UseCaseLogout

internal class UseCaseLogoutDefaultImpl(
    val authManger: AuthManager
):UseCaseLogout {
    override suspend fun logout() {
        authManger.logout()
    }
}