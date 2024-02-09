package com.abhijith.auth.viewmodel

import com.abhijith.auth.viewmodel.usecases.UseCaseAccountActivityMonitor
import com.abhijith.auth.viewmodel.usecases.UseCaseLogin
import com.abhijith.auth.viewmodel.usecases.UseCaseLogout
import com.abhijith.auth.viewmodel.usecases.UseCaseRegistration
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

interface SharedAuthViewModel {
    var toastChannel: Channel<String?>
    fun getLoginState(): Flow<UseCaseAccountActivityMonitor.Response>
    fun login(userName: String, password: String)
    fun register(userName: String, password: String)
    fun logout()
}

class SharedAuthViewModelImpl constructor(
    private val useCaseLogin: UseCaseLogin,
    private val userCaseRegistration: UseCaseRegistration,
    private val useCaseAccountActivityMonitor: UseCaseAccountActivityMonitor,
    private val viewModelScope: CoroutineScope = CoroutineScope(Dispatchers.IO + Job()),
    private val useCaseLogout: UseCaseLogout
) : SharedAuthViewModel {

    override fun getLoginState(): Flow<UseCaseAccountActivityMonitor.Response> {
        return useCaseAccountActivityMonitor.getUserLoginActivity()
    }


    override var toastChannel: Channel<String?> = Channel()
    override fun login(
        userName: String,
        password: String
    ) {
        viewModelScope.launch {
            val message = when (useCaseLogin.login(userName, password)) {
                UseCaseLogin.Result.SUCCESS -> "Login successful"
                UseCaseLogin.Result.INVALID_PASSWORD -> "Please enter valid password"
                UseCaseLogin.Result.INVALID_EMAIL_ID -> "Please enter valid email id"
                UseCaseLogin.Result.CLIENT_SIDE_ERROR -> "Oops! something went wrong. " +
                        "please check your internet connection and try again"
                UseCaseLogin.Result.SERVER_SIDE_ISSUE,
                UseCaseLogin.Result.UNKNOWN_ERROR -> "Oops! something went wrong " +
                        "try after some time"
            }
            toastChannel.send(message)
        }
    }

    override fun register(userName: String, password: String) {
        viewModelScope.launch {
            val it = userCaseRegistration.register(
                userName, password
            )
            println(it)
        }
    }

    override fun logout() {
        viewModelScope.launch {
            useCaseLogout.logout()
        }
    }

}