package com.shared.auth.viewmodel

import arrow.core.Option
import com.shared.auth.models.LoginResult
import com.shared.auth.models.RegistrationResult
import com.shared.compose_foundation.viewmodel.SharedViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface SharedAuthViewModel : SharedViewModel {
    fun getLoginState(): StateFlow<Option<String>>
    fun login(userName: String, password: String): Flow<LoginResult>
    fun register(userName: String, password: String): Flow<RegistrationResult>
    fun logout()
}

