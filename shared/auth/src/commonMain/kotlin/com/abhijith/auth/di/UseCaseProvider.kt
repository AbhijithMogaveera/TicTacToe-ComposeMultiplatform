package com.abhijith.auth.di


import com.abhijith.auth.viewmodel.usecases.UseCaseLogin
import com.abhijith.auth.viewmodel.usecases.UseCaseLogout
import com.abhijith.auth.viewmodel.usecases.UseCaseRegistration
import com.abhijith.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.abhijith.auth.viewmodel.usecases.impl.UseCaseLoginDefaultImpl
import com.abhijith.auth.viewmodel.usecases.impl.UseCaseLogoutDefaultImpl
import com.abhijith.auth.viewmodel.usecases.impl.UseCaseRegistrationDefaultImpl
import com.abhijith.auth.viewmodel.usecases.impl.UseCaseGetAuthTokenDefaultImpl
import org.koin.dsl.module

val UseCaseProvider = module {

    single<UseCaseLogin> {
        UseCaseLoginDefaultImpl(
            userAccountUtil = get()
        )
    }

    single<UseCaseGetAuthToken> {
        UseCaseGetAuthTokenDefaultImpl(
            authManger = get()
        )
    }

    single <UseCaseRegistration>{
        UseCaseRegistrationDefaultImpl(userAccountUtil = get())
    }

    single<UseCaseLogout> {
        UseCaseLogoutDefaultImpl(
            userAccountUtil = get()
        )
    }
}