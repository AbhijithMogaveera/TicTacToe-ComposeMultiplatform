package com.shared.auth.viewmodel.usecases

import com.shared.auth.models.RegistrationResult

internal interface UseCaseRegistration {
    suspend fun register(userName:String, password:String): RegistrationResult
}