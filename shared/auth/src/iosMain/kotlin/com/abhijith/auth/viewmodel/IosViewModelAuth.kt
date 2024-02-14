package com.abhijith.auth.viewmodel

import com.abhijith.auth.viewmodel.usecases.UseCaseAccountActivityMonitor
import com.abhijith.foundation.flow.CommonFlow
import com.abhijith.foundation.flow.toCommonFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IosViewModelAuth constructor(): KoinComponent, SharedAuthViewModel {

    val sharedAuthViewModel by inject<SharedAuthViewModelImpl>()

    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun getLoginState(
    ): CommonFlow<UseCaseAccountActivityMonitor.Response> {
       return sharedAuthViewModel.getLoginState().toCommonFlow()
    }

    override fun login(
        userName: String,
        password: String
    ) = sharedAuthViewModel.login(userName, password)


    override fun register(
        userName: String,
        password: String
    ) = sharedAuthViewModel.register(userName, password)


    override fun logout() = sharedAuthViewModel.logout()

}