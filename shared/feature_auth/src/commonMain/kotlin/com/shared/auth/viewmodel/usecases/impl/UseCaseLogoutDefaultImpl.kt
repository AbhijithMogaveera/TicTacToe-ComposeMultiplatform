package com.shared.auth.viewmodel.usecases.impl

import com.shared.auth.util.UserAccountsManager
import com.shared.auth.viewmodel.usecases.UseCaseLogout

internal class UseCaseLogoutDefaultImpl(
    val userAccountsManager: UserAccountsManager
):UseCaseLogout {
    override suspend fun logout() {
        userAccountsManager.logout()
    }
}