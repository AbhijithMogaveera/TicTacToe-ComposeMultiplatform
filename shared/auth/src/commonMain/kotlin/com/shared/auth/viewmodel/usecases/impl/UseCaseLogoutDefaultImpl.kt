package com.shared.auth.viewmodel.usecases.impl

import com.shared.auth.util.UserAccountUtil
import com.shared.auth.viewmodel.usecases.UseCaseLogout

internal class UseCaseLogoutDefaultImpl(
    val userAccountUtil: UserAccountUtil
):UseCaseLogout {
    override suspend fun logout() {
        userAccountUtil.logout()
    }
}