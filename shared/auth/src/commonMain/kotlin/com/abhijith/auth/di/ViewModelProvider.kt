package com.abhijith.auth.di

import com.abhijith.auth.viewmodel.SharedAuthViewModel
import com.abhijith.auth.viewmodel.SharedAuthViewModelImpl
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