package com.shared.auth.di

import com.shared.auth.viewmodel.AuthViewModel
import org.koin.dsl.module

val DefaultViewModelProvider = module {
    factory<AuthViewModel> {
        AuthViewModel()
    }
}