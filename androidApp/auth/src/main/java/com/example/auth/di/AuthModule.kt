package com.example.auth.di

import android.app.Application
import com.abhijith.auth.di.SharedAuthModule
import com.abhijith.auth.di.SharedAuthModuleDefaultImpl

object AuthModule {

    fun providesSharedAuthModule(
        application: Application
    ): SharedAuthModule = SharedAuthModuleDefaultImpl(
        application
    )

    fun providesLoginUseCase(
        sharedAuthModule: SharedAuthModule
    ) = sharedAuthModule.loginUseCase()

    fun providesAccountDetailsUseCase(
        sharedAuthModule: SharedAuthModule
    ) = sharedAuthModule.getAccountDetailsUseCase()
}