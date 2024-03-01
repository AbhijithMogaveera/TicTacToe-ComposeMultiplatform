package com.abhijith.auth

import arrow.core.None
import arrow.core.Some
import com.abhijith.auth.di.AuthApiProvider
import com.abhijith.auth.di.DefaultViewModelProvider
import com.abhijith.auth.di.UseCaseProvider
import com.abhijith.auth.di.UtilityProvider
import com.abhijith.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.abhijith.foundation.ktor.RequestResponseInterceptor
import com.abhijith.foundation.ktor.TokenInterceptorPlugin
import com.abhijith.foundation.ktor.addInterceptor
import com.abhijith.foundation.module_config.ModuleConfig
import com.abhijith.foundation.usecase.SyncUseCase
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

abstract class SharedAuthModuleConfig : ModuleConfig, RequestResponseInterceptor, KoinComponent {
    private val useCaseGetAuthToken: UseCaseGetAuthToken by lazy { get() }
    override fun configKoinModules(
        koinApplication: KoinApplication
    ) {
        println("SharedAuthModuleConfigured")
        koinApplication.modules(
            UseCaseProvider,
            AuthApiProvider,
            UtilityProvider
        )
        koinApplication.modules(DefaultViewModelProvider)
    }

    override fun onKoinConfigurationFinish() {
        super.onKoinConfigurationFinish()
        TokenInterceptorPlugin.addInterceptor(this)
    }

    override suspend fun onRequest(req: HttpRequestBuilder) {
        when (val it = useCaseGetAuthToken.getToken().value) {
            None -> {}
            is Some -> {
                req.header(HttpHeaders.Authorization, "Bearer ${it.value}")
            }
        }
    }

    override fun getSyncUseCases(): List<SyncUseCase> {
        return listOf(

        )
    }
}