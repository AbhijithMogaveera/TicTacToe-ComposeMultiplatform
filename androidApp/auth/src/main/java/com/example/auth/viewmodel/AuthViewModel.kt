package com.example.auth.viewmodel

import androidx.lifecycle.ViewModel
import com.abhijith.auth.viewmodel.SharedAuthViewModel
import com.abhijith.auth.viewmodel.usecases.AccountDetailsUseCase
import com.abhijith.auth.viewmodel.usecases.LoginUseCase
class AuthViewModel
constructor(
    private val loginUseCase: LoginUseCase,
    private val accountDetailsUseCase: AccountDetailsUseCase
) : ViewModel() {
    val shared by lazy {
        SharedAuthViewModel(
            loginUseCase = loginUseCase,
            accountDetailsUseCase = accountDetailsUseCase
        )
    }
}