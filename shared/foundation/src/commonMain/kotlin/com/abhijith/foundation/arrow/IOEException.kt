package com.abhijith.foundation.arrow

import arrow.core.Either
import arrow.core.raise.either
import com.abhijith.foundation.exceptions.RequestFailure
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.withContext

inline fun <T> action(action:  () -> T): Either<RequestFailure, T> {
    return either {
        try {
            action()
        } catch (e: Exception) {
            raise(RequestFailure(e))
        }
    }
}

