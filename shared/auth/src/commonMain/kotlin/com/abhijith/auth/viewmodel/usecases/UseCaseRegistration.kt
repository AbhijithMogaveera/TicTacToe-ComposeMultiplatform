package com.abhijith.auth.viewmodel.usecases

import com.abhijith.auth.models.RegistrationResult

internal interface UseCaseRegistration {

    suspend fun register(userName:String, password:String): RegistrationResult
}