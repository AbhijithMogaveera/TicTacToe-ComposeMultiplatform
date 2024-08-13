package com.shared.auth.di

import com.shared.auth.util.AuthManager
import org.koin.dsl.module

val  AccountManagerProvider = module {
    single<AuthManager> {
        AuthManager(
            authApis = get(),
            preference = get(),
        )
    }
}