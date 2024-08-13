package com.shared.auth.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import arrow.core.None
import arrow.core.Option
import arrow.core.none
import arrow.core.some
import com.shared.auth.models.LoginResult
import com.shared.auth.models.RegistrationResult
import com.shared.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.shared.auth.viewmodel.usecases.UseCaseLogin
import com.shared.auth.viewmodel.usecases.UseCaseRegistration
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {

    private val useCaseLogin: UseCaseLogin by inject()
    private val userCaseRegistration: UseCaseRegistration by inject()
    private val useCaseGetAuthToken: UseCaseGetAuthToken by inject()

    var isLoginIsInProgress by mutableStateOf(false)
    var loginResult: Option<LoginResult> by mutableStateOf(none())
    fun login(
        userName: String,
        password: String
    ) {
        viewModelScope.launch {
            loginResult = None
            loginResult = useCaseLogin.login(userName.trim(), password.trim()).some()
        }
    }

    var isRegistrationIsInProgress by mutableStateOf(false)
    var registrationResult:Option<RegistrationResult> by mutableStateOf(none())
    fun register(userName: String, password: String){
        viewModelScope.launch {
            registrationResult = None
            registrationResult = userCaseRegistration.register(userName.trim(), password.trim()).some()
        }
    }

    val isUserLoggedIn:Flow<Boolean> = useCaseGetAuthToken.getToken().map {
        it.isSome()
    }

}