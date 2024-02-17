package com.abhijith.foundation.ktor.exceptions

import com.abhijith.foundation.ktor.ClientSideError
import com.abhijith.foundation.ktor.ServerSideError
import io.ktor.utils.io.errors.IOException

class RequestFailure(
    val exception: Throwable
) {
    inline fun isClientSideError(action: (ClientSideError) -> Unit) = apply {
        if (this.exception is ClientSideError)
            action(exception)
    }

    inline fun isServerSideError(action: (ServerSideError) -> Unit) = apply {
        if (this.exception is ServerSideError)
            action(exception)
    }

    inline fun isIOEException(action: (IOException) -> Unit) = apply {
        if (exception is IOException)
            exception.apply(action)
    }

    inline fun isUnKnownError(action: Throwable.() -> Unit) = apply {
        if (
            exception !is ServerSideError
            && exception !is ClientSideError
            && exception !is IOException
        ) {
            exception.apply(action)
        }
    }


}