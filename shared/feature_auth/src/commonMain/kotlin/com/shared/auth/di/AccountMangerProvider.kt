package com.shared.auth.di

import com.shared.auth.util.UserAccountsManager
import org.koin.dsl.module

val  AccountMangerProvider = module {
    single<UserAccountsManager> {
        UserAccountsManager(
            authApis = get(),
            preference = get(),
        )
    }
}