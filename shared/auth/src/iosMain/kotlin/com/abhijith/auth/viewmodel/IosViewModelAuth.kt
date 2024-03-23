package com.abhijith.auth.viewmodel

import arrow.core.Option
import com.abhijith.auth.models.LoginResult
import com.abhijith.auth.models.RegistrationResult
import com.abhijith.foundation.flow.CommonFlow
import com.abhijith.foundation.flow.CommonStateFlow
import com.abhijith.foundation.flow.toCommonFlow
import com.abhijith.foundation.flow.toCommonStateFlow
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class IosViewModelAuth constructor(): KoinComponent, SharedAuthViewModel {

    private val sharedAuthViewModel by inject<SharedAuthViewModel>()

    override val coroutineScope: CoroutineScope = CoroutineScope(Dispatchers.Main)

    override fun getLoginState(
    ): CommonStateFlow<Option<String>> {
       return sharedAuthViewModel.getLoginState().toCommonStateFlow()
    }

    override fun login(
        userName: String,
        password: String
    ): CommonFlow<LoginResult> {
        return sharedAuthViewModel.login(userName, password).toCommonFlow()
    }


    override fun register(
        userName: String,
        password: String
    ): CommonFlow<RegistrationResult> {
        return sharedAuthViewModel.register(userName, password).toCommonFlow()
    }


    override fun logout() = sharedAuthViewModel.logout()

}