package com.abhijith.auth.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.abhijith.auth.viewmodel.usecases.UseCaseAccountActivityMonitor
import com.abhijith.auth.viewmodel.usecases.UseCaseLogin
import com.abhijith.auth.viewmodel.usecases.UseCaseLogout
import com.abhijith.auth.viewmodel.usecases.UseCaseRegistration
import kotlinx.coroutines.CoroutineScope

class AndroidViewModelAuth
constructor(
    private val useCaseLogin: UseCaseLogin,
    private val useCaseAccountActivityMonitor: UseCaseAccountActivityMonitor,
    private val userCaseRegistration: UseCaseRegistration,
    private val useCaseLogout: UseCaseLogout
) : ViewModel(), SharedAuthViewModel by SharedAuthViewModelImpl(
    useCaseLogin = useCaseLogin,
    useCaseAccountActivityMonitor = useCaseAccountActivityMonitor,
    userCaseRegistration = userCaseRegistration,
    useCaseLogout = useCaseLogout,
) {
    override var coroutineScope: CoroutineScope = viewModelScope
}