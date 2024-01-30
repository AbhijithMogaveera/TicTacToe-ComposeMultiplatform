package com.abhijith.auth.viewmodel

import com.abhijith.auth.viewmodel.usecases.AccountDetailsUseCase
import com.abhijith.auth.viewmodel.usecases.LoginUseCase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.launch

open class SharedAuthViewModel(
    private val loginUseCase: LoginUseCase,
    private val accountDetailsUseCase: AccountDetailsUseCase,
    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job())
) {

    fun getLoginState() = accountDetailsUseCase.getUserLoginActivity()


    var toastChannel: Channel<String?> = Channel()
    fun login(
        userName: String,
        password: String
    ) {
        viewModelScope.launch {
            val message = when (loginUseCase.login(userName, password)) {
                LoginUseCase.Result.SUCCESS -> "Login successful"
                LoginUseCase.Result.INVALID_PASSWORD -> "Please enter valid password"
                LoginUseCase.Result.INVALID_EMAIL_ID -> "Please enter valid email id"
                LoginUseCase.Result.CLIENT_SIDE_ERROR -> "Oops! something went wrong. " +
                        "please check your internet connection and try again"

                LoginUseCase.Result.SERVER_SIDE_ISSUE,
                LoginUseCase.Result.UNKNOWN_ERROR -> "Oops! something went wrong " +
                        "try after some time"
            }
            toastChannel.send(message)
        }
    }

}