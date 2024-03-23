package com.abhijith.foundation.arrow

import arrow.core.Either
import arrow.core.raise.either
import com.abhijith.foundation.ktor.exceptions.RequestFailure

inline fun <T> apiCallScope(action:  () -> T): Either<RequestFailure, T> {
    return either {
        try {
            action()
        } catch (e: Exception) {
            raise(RequestFailure(e))
        }
    }
}

