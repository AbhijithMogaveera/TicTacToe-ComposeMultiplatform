package com.abhijith.foundation.exceptions

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

    inline fun isIOEException(action: (IOException) -> Unit): RequestFailure = apply {
        if (exception is IOException)
            exception.apply(action)
    }

    inline fun onException(action: Throwable.() -> Unit): RequestFailure = apply {
        exception.apply(action)
    }


}