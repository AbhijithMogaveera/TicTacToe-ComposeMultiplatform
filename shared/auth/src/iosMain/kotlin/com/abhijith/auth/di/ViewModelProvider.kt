package com.abhijith.auth.di

import com.abhijith.auth.viewmodel.SharedAuthViewModelImpl
import org.koin.dsl.module

val IosViewModelProvider = module {

    single<SharedAuthViewModelImpl> {
        SharedAuthViewModelImpl(
            useCaseLogin = get(),
            userCaseRegistration = get(),
            useCaseAccountActivityMonitor = get(),
            useCaseLogout = get()
        )
    }
}