package com.shared.auth.di

import com.shared.auth.viewmodel.AndroidViewModelAuth
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val AndroidViewModelProvider = module {
    viewModelOf(::AndroidViewModelAuth)
}