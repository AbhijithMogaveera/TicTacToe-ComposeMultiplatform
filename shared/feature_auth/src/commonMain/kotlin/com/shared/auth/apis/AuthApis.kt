package com.shared.auth.apis

import arrow.core.Either
import com.shared.compose_foundation.ktor.exceptions.RequestFailure

/**
 * Interface defining the authentication APIs for login and registration.
 *
 * This interface provides methods for user authentication operations, including login and registration.
 * Each method returns an `Either` type that represents a result that can either be a success with a `JwtToken`
 * or a failure with a `RequestFailure`.
 */
interface AuthApis {

    /**
     * Logs in a user with the provided credentials.
     *
     * @param userName The username of the user attempting to log in.
     * @param password The password of the user attempting to log in.
     * @return An `Either` type containing a `JwtToken` on success or a `RequestFailure` on failure.
     */
    suspend fun login(
        userName: String,
        password: String
    ): Either<RequestFailure, JwtToken>

    /**
     * Registers a new user with the provided credentials.
     *
     * @param userName The desired username for the new user.
     * @param password The desired password for the new user.
     * @return An `Either` type containing a `JwtToken` on success or a `RequestFailure` on failure.
     */
    suspend fun registration(
        userName: String,
        password: String
    ): Either<RequestFailure, JwtToken>
}
