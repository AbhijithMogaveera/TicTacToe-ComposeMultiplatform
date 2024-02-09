package com.abhijith.auth.viewmodel.usecases

import com.abhijith.auth.util.UserDetails
import kotlinx.coroutines.flow.StateFlow

interface UseCaseCurrentUserDetails {
    fun getDetails():StateFlow<UserDetails>
}