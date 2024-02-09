package com.abhijith.auth.viewmodel.usecases.impl

import com.abhijith.auth.util.UserAccountUtil
import com.abhijith.auth.util.UserDetails
import com.abhijith.auth.viewmodel.usecases.UseCaseAccountActivityMonitor
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class UseCaseAccountActivityMonitorDefaultImpl(
    val userAccountUtil: UserAccountUtil
) : UseCaseAccountActivityMonitor {
    override fun getUserLoginActivity(): Flow<UseCaseAccountActivityMonitor.Response> {
        return userAccountUtil.userDetails.map {
            when (it) {
                is UserDetails.Found -> UseCaseAccountActivityMonitor.Response.LoggedInUser(
                    userName = it.user.userName,
                    token = it.user.token
                )
                UserDetails.Loading -> UseCaseAccountActivityMonitor.Response.NoLogin
                UserDetails.UserNotFound -> UseCaseAccountActivityMonitor.Response.NoLogin
            }
        }
    }

}