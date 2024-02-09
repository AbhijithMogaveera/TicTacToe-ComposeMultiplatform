package com.abhijith.auth.di

import com.abhijith.auth.apis.AuthApis
import com.abhijith.auth.apis.AuthApisDefaultImpl
import com.abhijith.foundation.network.httpClient
import org.koin.dsl.module

val AuthApiProvider = module {
    single<AuthApis> {
        AuthApisDefaultImpl(httpClient = httpClient)
    }
}