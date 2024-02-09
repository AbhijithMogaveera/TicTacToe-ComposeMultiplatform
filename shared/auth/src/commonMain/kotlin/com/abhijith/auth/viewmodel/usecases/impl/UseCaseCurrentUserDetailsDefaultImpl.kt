package com.abhijith.auth.viewmodel.usecases.impl

import com.abhijith.auth.util.UserAccountUtil
import com.abhijith.auth.util.UserDetails
import com.abhijith.auth.viewmodel.usecases.UseCaseCurrentUserDetails
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class UseCaseCurrentUserDetailsDefaultImpl(
    val authManger: UserAccountUtil
):UseCaseCurrentUserDetails {
    override fun getDetails(): StateFlow<UserDetails> {
        return authManger.userDetails.asStateFlow()
    }
}