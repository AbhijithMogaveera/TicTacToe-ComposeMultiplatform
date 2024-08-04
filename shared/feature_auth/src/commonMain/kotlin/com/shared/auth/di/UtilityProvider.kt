package com.shared.auth.di

import com.shared.auth.util.UserAccountUtil
import org.koin.dsl.module

val  UtilityProvider = module {
    single<UserAccountUtil> {
        UserAccountUtil(
            authApis = get(),
            preference = get(),
        )
    }
}