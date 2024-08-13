package com.shared.auth.di


import com.shared.auth.viewmodel.usecases.UseCaseLogin
import com.shared.auth.viewmodel.usecases.UseCaseLogout
import com.shared.auth.viewmodel.usecases.UseCaseRegistration
import com.shared.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.shared.auth.viewmodel.usecases.impl.UseCaseLoginDefaultImpl
import com.shared.auth.viewmodel.usecases.impl.UseCaseLogoutDefaultImpl
import com.shared.auth.viewmodel.usecases.impl.UseCaseRegistrationDefaultImpl
import com.shared.auth.viewmodel.usecases.impl.UseCaseGetAuthTokenDefaultImpl
import org.koin.dsl.module

val UseCaseProvider = module {

    single<UseCaseLogin> {
        UseCaseLoginDefaultImpl(
            authManger = get()
        )
    }

    single<UseCaseGetAuthToken> {
        UseCaseGetAuthTokenDefaultImpl(
            authManger = get()
        )
    }

    single <UseCaseRegistration>{
        UseCaseRegistrationDefaultImpl(authManger = get())
    }

    single<UseCaseLogout> {
        UseCaseLogoutDefaultImpl(
            authManger = get()
        )
    }
}