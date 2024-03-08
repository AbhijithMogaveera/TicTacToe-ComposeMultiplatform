package com.abhijith.auth.viewmodel.usecases

import arrow.core.Option
import com.abhijith.foundation.module_config.SharedWithOtherModule
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

@SharedWithOtherModule
interface UseCaseGetAuthToken {
    fun getToken(): StateFlow<Option<String>>
}