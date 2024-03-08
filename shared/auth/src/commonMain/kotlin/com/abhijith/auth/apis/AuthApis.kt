package com.abhijith.auth.apis

import arrow.core.Either
import com.abhijith.foundation.ktor.exceptions.RequestFailure

internal interface AuthApis {

    suspend fun login(
        userName:String,
        password:String
    ): Either<RequestFailure, JwtToken>

    suspend fun registration(
        userName: String,
        password: String
    ): Either<RequestFailure, JwtToken>

}