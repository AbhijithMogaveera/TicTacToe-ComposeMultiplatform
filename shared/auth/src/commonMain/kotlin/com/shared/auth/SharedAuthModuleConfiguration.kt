package com.shared.auth

import arrow.core.None
import arrow.core.Some
import com.shared.auth.di.AuthApiProvider
import com.shared.auth.di.DefaultViewModelProvider
import com.shared.auth.di.UseCaseProvider
import com.shared.auth.di.UtilityProvider
import com.shared.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.shared.compose_foundation.ktor.RequestResponseInterceptor
import com.shared.compose_foundation.ktor.TokenInterceptorPlugin
import com.shared.compose_foundation.ktor.addInterceptor
import com.shared.compose_foundation.module_config.ModuleConfiguration
import com.shared.compose_foundation.usecase.SyncUseCase
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.get

abstract class SharedAuthModuleConfiguration (
): ModuleConfiguration, RequestResponseInterceptor, KoinComponent {
    private val useCaseGetAuthToken: UseCaseGetAuthToken by lazy { get() }
    override fun configKoinModules(
        koinApplication: KoinApplication
    ) {
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