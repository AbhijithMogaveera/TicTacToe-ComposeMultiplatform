package com.shared.auth.di

import com.shared.auth.apis.AuthApis
import com.shared.auth.apis.AuthApisDefaultImpl
import com.shared.compose_foundation.ktor.client.httpClient
import org.koin.dsl.module

val AuthApiProvider = module {
    single<AuthApis> {
        AuthApisDefaultImpl(httpClient = httpClient)
    }
}