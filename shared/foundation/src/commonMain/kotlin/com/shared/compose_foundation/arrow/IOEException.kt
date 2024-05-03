package com.shared.compose_foundation.arrow

import arrow.core.Either
import arrow.core.raise.either
import com.shared.compose_foundation.ktor.exceptions.RequestFailure

inline fun <T> apiCallScope(action:  () -> T): Either<RequestFailure, T> {
    return either {
        try {
            action()
        } catch (e: Exception) {
            raise(RequestFailure(e))
        }
    }
}

