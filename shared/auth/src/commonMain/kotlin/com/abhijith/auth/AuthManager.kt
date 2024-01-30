package com.abhijith.auth

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.raise.either
import com.abhijith.auth.apis.AuthApis
import com.abhijith.auth.model.User
import com.abhijith.foundation.exceptions.RequestFailure
import com.abhijith.foundation.prefrence.KmmPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

sealed class UserState {
    data object Loading : UserState()
    data object UserNotFound : UserState()
    class Found(val user: User) : UserState()
}

class AuthManager(
    private val preference: KmmPreference,
    private val authApis: AuthApis,
    private val appScope:CoroutineScope
) {

    val loggedInUser = MutableStateFlow<UserState>(UserState.Loading)

    init {
        appScope.launch {
            val userName = preference.getString(USER_NAME)
            val token = preference.getString(KEY_TOKEN)
            if (userName == null || token == null) {
                loggedInUser.emit(UserState.UserNotFound)
            } else {
                loggedInUser.emit(UserState.Found(User(userName, token)))
            }
        }
    }

    companion object {
        private const val KEY_TOKEN = "AuthManager::Token"
        private const val USER_NAME = "AuthManager::Username"
    }

    suspend fun login(
        userName: String,
        password: String
    ): Either<RequestFailure, Unit> = either {

        val token = authApis.login(
            userName,
            password
        ).getOrElse(::raise)

        preference.put(
            key = KEY_TOKEN,
            value = token
        )

        preference.put(
            key = USER_NAME,
            value = userName
        )

        loggedInUser.emit(UserState.Found(User(userName, token)))
    }

    suspend fun register(
        userName: String,
        password: String
    ): Either<RequestFailure, Unit> = either {
        val token = authApis.registration(userName, password).getOrElse(::raise)
    }

    suspend fun logout() {
        preference.removeKey(KEY_TOKEN)
        loggedInUser.emit(UserState.UserNotFound)
    }
}