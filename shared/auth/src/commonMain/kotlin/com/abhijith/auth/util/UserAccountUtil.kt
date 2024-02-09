package com.abhijith.auth.util

import arrow.core.Either
import arrow.core.getOrElse
import arrow.core.raise.either
import com.abhijith.auth.apis.AuthApis
import com.abhijith.auth.apis.JwtToken
import com.abhijith.auth.model.User
import com.abhijith.foundation.exceptions.RequestFailure
import com.abhijith.foundation.prefrence.KmmPreference
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

sealed class UserDetails {
    data object Loading : UserDetails()
    data object UserNotFound : UserDetails()
    class Found(val user: User) : UserDetails()
}

class UserAccountUtil(
    private val preference: KmmPreference,
    private val authApis: AuthApis,
    private val appScope:CoroutineScope
) {

    val userDetails = MutableStateFlow<UserDetails>(UserDetails.Loading)
    init {
        appScope.launch {
            userDetails.collectLatest {
                when(it){
                    is UserDetails.Found -> {
                        println("User details found ${it.user}")
                    }
                    UserDetails.Loading -> {
                        println("user details are loading")
                    }
                    UserDetails.UserNotFound -> {
                        println("User data not found")
                    }
                }
            }
        }
    }

    init {
        appScope.launch {
            val userName = preference.getString(KEY_USER_NAME)
            val token = preference.getString(KEY_TOKEN)
            if (userName == null || token == null) {
                userDetails.emit(UserDetails.UserNotFound)
            } else {
                userDetails.emit(UserDetails.Found(User(userName, token)))
            }
        }
    }

    companion object {
        private const val KEY_TOKEN = "UserDetails::Token"
        private const val KEY_USER_NAME = "UserDetails::Username"
    }

    suspend fun login(
        userName: String,
        password: String
    ): Either<RequestFailure, Unit> = either {

        val token = authApis.login(
            userName,
            password
        ).getOrElse(::raise)

        saveTokenAndUserName(token, userName)

        userDetails.emit(UserDetails.Found(User(userName, token)))
    }

    private fun saveTokenAndUserName(token: JwtToken, userName: String) {
        preference.put(
            key = KEY_TOKEN,
            value = token
        )

        preference.put(
            key = KEY_USER_NAME,
            value = userName
        )
    }

    suspend fun register(
        userName: String,
        password: String
    ): Either<RequestFailure, Unit> = either {
        val token = authApis
            .registration(userName, password)
            .getOrElse(::raise)
        saveTokenAndUserName(token, userName)
        userDetails.emit(UserDetails.Found(User(userName, token)))
    }

    suspend fun logout() {
        removeTokenAndUserName()
        userDetails.emit(UserDetails.UserNotFound)
    }

    private fun removeTokenAndUserName() {
        preference.removeKey(KEY_TOKEN)
        preference.removeKey(KEY_USER_NAME)
    }
}