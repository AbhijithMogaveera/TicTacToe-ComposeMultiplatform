package com.shared.auth.util

import arrow.core.Either
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.raise.either
import arrow.core.some
import com.shared.auth.apis.AuthApis
import com.shared.auth.apis.JwtToken
import com.shared.compose_foundation.StartUpTask
import com.shared.compose_foundation.ktor.exceptions.RequestFailure
import com.shared.compose_foundation.prefrence.KmmPreference
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

/**
 * A manager class responsible for handling user authentication, including login, registration,
 * token management, and user logout.
 *
 * @property preference An instance of `KmmPreference` used to store and retrieve authentication tokens.
 * @property authApis An instance of `AuthApis` that provides the login and registration API methods.
 */
class AuthManager(
    private val preference: KmmPreference,
    private val authApis: AuthApis,
) : StartUpTask {

    /**
     * A `MutableStateFlow` that holds the current authentication token as an `Option<String>`.
     * It emits `None` when the user is logged out or when no token is available.
     */
    var token: MutableStateFlow<Option<String>> = MutableStateFlow(None)

    companion object {
        private const val KEY_TOKEN = "UserDetails::Token"
    }

    /**
     * Logs in the user with the provided credentials.
     *
     * @param userName The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return An `Either<RequestFailure, Unit>` indicating the success or failure of the login attempt.
     */
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

    /**
     * Saves the authentication token and associated user details.
     *
     * @param token The `JwtToken` to be saved.
     */
    private suspend fun saveTokenAndUserName(token: JwtToken) {
        preference.put(key = KEY_TOKEN, value = token)
        this.token.emit(token.some())
    }

    /**
     * Registers a new user with the provided credentials.
     *
     * @param userName The desired username for the new user.
     * @param password The desired password for the new user.
     * @return An `Either<RequestFailure, Unit>` indicating the success or failure of the registration attempt.
     */
    suspend fun register(
        userName: String,
        password: String
    ): Either<RequestFailure, Unit> = either {
        val token = authApis
            .registration(userName, password)
            .getOrElse(::raise)
        saveTokenAndUserName(token)
    }

    /**
     * Logs out the user by clearing the saved authentication token.
     */
    suspend fun logout() {
        token.emit(None)
        preference.removeKey(KEY_TOKEN)
    }

    /**
     * Initializes the manager by loading the last saved authentication token from preferences,
     * if available, and updating the `token` flow
     */
    override fun execute() {
        token.update { _ ->
            when (val lastSavedToken = preference.getString(KEY_TOKEN)) {
                null -> None
                else -> lastSavedToken.some()
            }
        }
    }
}
