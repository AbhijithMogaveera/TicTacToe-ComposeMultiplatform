package com.abhijith.auth.viewmodel.usecases.impl

import com.abhijith.auth.util.UserAccountUtil
import com.abhijith.auth.viewmodel.usecases.UseCaseLogout

class UseCaseLogoutDefaultImpl(
    val userAccountUtil: UserAccountUtil
):UseCaseLogout {
    override suspend fun logout() {
        userAccountUtil.logout()
    }
}