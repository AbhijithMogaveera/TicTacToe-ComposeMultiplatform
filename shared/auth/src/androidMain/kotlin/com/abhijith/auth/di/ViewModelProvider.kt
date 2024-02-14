package com.abhijith.auth.di

import com.abhijith.auth.viewmodel.AndroidViewModelAuth
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val ViewModelProvider = module {
    viewModelOf(::AndroidViewModelAuth)
}