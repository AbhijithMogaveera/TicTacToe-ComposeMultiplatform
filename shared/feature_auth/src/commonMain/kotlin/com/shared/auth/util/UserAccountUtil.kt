package com.shared.auth.util

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.raise.either
import arrow.core.some
import com.shared.auth.apis.AuthApis
import com.shared.auth.apis.JwtToken
import com.shared.compose_foundation.ktor.exceptions.RequestFailure
import com.shared.compose_foundation.prefrence.KmmPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class UserAccountUtil constructor(
    private val preference: KmmPreference,
    private val authApis: AuthApis,
) {

    var token: MutableStateFlow<Option<String>> = MutableStateFlow(None)

    init {
        token.update {
            when (val it = preference.getString(KEY_TOKEN)) {
                null -> None
                else -> it.some()
            }
        }
    }

    companion object {
        private const val KEY_TOKEN = "UserDetails::Token"
    }

    suspend fun login(
        userName: String,
        password: String
    ): Either<RequestFailure, Unit> = either {
        val token = authApis.login(
            userName,
            password
        ).getOrElse(::raise)
        saveTokenAndUserName(token)
    }

    private suspend fun saveTokenAndUserName(token: JwtToken) {
        preference.put(key = KEY_TOKEN, value = token)
        this.token.emit(token.some())
    }

    suspend fun register(
        userName: String,
        password: String
    ): Either<RequestFailure, Unit> = either {
        val token = authApis
            .registration(userName, password)
            .getOrElse(::raise)
        saveTokenAndUserName(token)
    }

    suspend fun logout() {
        token.emit(None)
        preference.removeKey(KEY_TOKEN)
    }
}