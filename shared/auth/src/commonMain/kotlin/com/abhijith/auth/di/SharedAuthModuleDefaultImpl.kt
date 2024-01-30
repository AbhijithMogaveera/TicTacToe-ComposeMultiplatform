package com.abhijith.auth.di

import com.abhijith.auth.AuthManager
import com.abhijith.auth.apis.AuthApis
import com.abhijith.auth.apis.AuthApisDefaultImpl
import com.abhijith.auth.viewmodel.usecases.AccountDetailsUseCase
import com.abhijith.auth.viewmodel.usecases.AccountDetailsUseCaseDefaultImpl
import com.abhijith.auth.viewmodel.usecases.LoginUseCase
import com.abhijith.auth.viewmodel.usecases.LoginUseCaseDefaultImpl
import com.abhijith.foundation.coroutines.appScope
import com.abhijith.foundation.network.httpClient
import com.abhijith.foundation.platform.Kontext
import com.abhijith.foundation.prefrence.KmmPreference
import com.abhijith.foundation.prefrence.preference
import kotlinx.coroutines.CoroutineScope

interface SharedAuthModule {
    fun getAuthApi(): AuthApis
    fun getAuthManager(): AuthManager
    fun getAccountDetailsUseCase(): AccountDetailsUseCase
    fun loginUseCase(): LoginUseCase
}

class SharedAuthModuleDefaultImpl(
    private val kontext: Kontext,
    private val preference: KmmPreference = kontext.preference,
    private val appScope: CoroutineScope = kontext.appScope
) : SharedAuthModule {

    private val authApis by lazy {
        AuthApisDefaultImpl(httpClient)
    }
    private val accountDetailsUseCase by lazy {
        AccountDetailsUseCaseDefaultImpl(authManger)
    }

    private val authManger by lazy {
        AuthManager(preference, authApis, appScope)
    }

    override fun getAuthApi(): AuthApis {
        return authApis
    }

    override fun getAuthManager(): AuthManager {
        return authManger
    }

    override fun getAccountDetailsUseCase(): AccountDetailsUseCase {
        return accountDetailsUseCase
    }

    override fun loginUseCase(): LoginUseCase {
        return LoginUseCaseDefaultImpl(authManger)
    }
}