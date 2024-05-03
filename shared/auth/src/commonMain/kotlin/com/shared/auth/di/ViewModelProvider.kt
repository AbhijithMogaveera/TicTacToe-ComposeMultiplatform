package com.shared.auth.di

import com.shared.auth.viewmodel.SharedAuthViewModel
import com.shared.auth.viewmodel.SharedAuthViewModelImpl
import org.koin.dsl.module

val DefaultViewModelProvider = module {
    factory<SharedAuthViewModel> {
        SharedAuthViewModelImpl(
            useCaseLogin = get(),
            userCaseRegistration = get(),
            useCaseGetAuthToken = get(),
            useCaseLogout = get()
        )
    }
}