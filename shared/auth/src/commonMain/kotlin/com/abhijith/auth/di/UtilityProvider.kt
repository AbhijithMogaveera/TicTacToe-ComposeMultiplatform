package com.abhijith.auth.di

import com.abhijith.auth.util.UserAccountUtil
import org.koin.dsl.module

val  UtilityProvider = module {
    single<UserAccountUtil> {
        UserAccountUtil(
            authApis = get(),
            preference = get(),
        )
    }
}