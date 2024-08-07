package com.shared.auth.apis

import arrow.core.Either
import com.shared.compose_foundation.ktor.exceptions.RequestFailure

interface AuthApis {

    suspend fun login(
        userName:String,
        password:String
    ): Either<RequestFailure, JwtToken>

    suspend fun registration(
        userName: String,
        password: String
    ): Either<RequestFailure, JwtToken>

}