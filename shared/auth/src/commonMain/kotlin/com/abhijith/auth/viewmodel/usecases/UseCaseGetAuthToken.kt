package com.abhijith.auth.viewmodel.usecases

import arrow.core.Option
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow

interface UseCaseGetAuthToken {
    fun getToken(): StateFlow<Option<String>>
}