package com.shared.auth.viewmodel.usecases

import arrow.core.Option
import com.shared.compose_foundation.module_config.SharedWithOtherModule
import kotlinx.coroutines.flow.StateFlow

@SharedWithOtherModule
interface UseCaseGetAuthToken {
    fun getToken(): StateFlow<Option<String>>
}