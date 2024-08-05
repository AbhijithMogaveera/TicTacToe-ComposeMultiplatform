package com.shared.auth.viewmodel

import androidx.lifecycle.ViewModel
import arrow.core.Option
import com.shared.auth.models.LoginResult
import com.shared.auth.models.RegistrationResult
import com.shared.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.shared.auth.viewmodel.usecases.UseCaseLogin
import com.shared.auth.viewmodel.usecases.UseCaseRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class AuthViewModel : ViewModel(), KoinComponent {

    private val useCaseLogin: UseCaseLogin by inject()
    private val userCaseRegistration: UseCaseRegistration by inject()
    private val useCaseGetAuthToken: UseCaseGetAuthToken by inject()

    fun getLoginState(): StateFlow<Option<String>> {
        return useCaseGetAuthToken.getToken()
    }

    fun login(
        userName: String,
        password: String
    ): Flow<LoginResult> = flow {
        val login = useCaseLogin.login(userName.trim(), password.trim())
        emit(login)
    }

    fun register(userName: String, password: String): Flow<RegistrationResult> =
        flow {
            val it = userCaseRegistration.register(
                userName, password
            )
            emit(it)
        }

}