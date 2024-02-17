package com.abhijith.auth.viewmodel

import com.abhijith.auth.viewmodel.usecases.UseCaseAccountActivityMonitor
import com.abhijith.auth.viewmodel.usecases.UseCaseLogin
import com.abhijith.auth.viewmodel.usecases.UseCaseLogout
import com.abhijith.auth.viewmodel.usecases.UseCaseRegistration
import com.abhijith.foundation.viewmodel.SharedViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.launch

interface SharedAuthViewModel : SharedViewModel {
    fun getLoginState(): Flow<UseCaseAccountActivityMonitor.Response>
    fun login(userName: String, password: String): Flow<UseCaseLogin.Result>
    fun register(userName: String, password: String): Flow<UseCaseRegistration.Result>
    fun logout()
}

class SharedAuthViewModelImpl constructor(
    private val useCaseLogin: UseCaseLogin,
    private val userCaseRegistration: UseCaseRegistration,
    private val useCaseAccountActivityMonitor: UseCaseAccountActivityMonitor,
    private val useCaseLogout: UseCaseLogout
) : SharedAuthViewModel {

    override var coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main + SupervisorJob())

    override fun getLoginState(): Flow<UseCaseAccountActivityMonitor.Response> {
        return useCaseAccountActivityMonitor.getUserLoginActivity()
    }

    override fun login(
        userName: String,
        password: String
    ): Flow<UseCaseLogin.Result> = flow {
        val login = useCaseLogin.login(userName.trim(), password.trim())
        emit(login)
    }

    override fun register(userName: String, password: String): Flow<UseCaseRegistration.Result> =
        flow {
            val it = userCaseRegistration.register(
                userName, password
            )
            emit(it)
        }

    override fun logout() {
        coroutineScope.launch {
            useCaseLogout.logout()
        }
    }

}