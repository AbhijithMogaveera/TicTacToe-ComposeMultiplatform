package com.shared.auth.di

import com.shared.auth.viewmodel.SharedAuthViewModel
import org.koin.dsl.module

val DefaultViewModelProvider = module {
    factory<SharedAuthViewModel> {
        SharedAuthViewModel()
    }
}