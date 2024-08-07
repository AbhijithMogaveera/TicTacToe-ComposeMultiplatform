package com.shared.auth

import arrow.core.None
import arrow.core.Some
import com.shared.auth.di.AuthApiProvider
import com.shared.auth.di.UseCaseProvider
import com.shared.auth.di.AccountMangerProvider
import com.shared.auth.viewmodel.usecases.UseCaseGetAuthToken
import com.shared.compose_foundation.koin.ModuleInitilizer
import com.shared.compose_foundation.ktor.RequestResponseInterceptor
import com.shared.compose_foundation.ktor.RestInterceptors
import com.shared.compose_foundation.ktor.addInterceptor
import io.ktor.client.request.HttpRequestBuilder
import io.ktor.client.request.header
import io.ktor.http.HttpHeaders
import org.koin.core.KoinApplication
import org.koin.core.component.get
object FeatureAuthInitilizer : ModuleInitilizer, RequestResponseInterceptor {
    private val useCaseGetAuthToken: UseCaseGetAuthToken by lazy { get() }
    override fun configKoinModules(koinApplication: KoinApplication) {
        koinApplication.modules(
            UseCaseProvider,
            AuthApiProvider,
            AccountMangerProvider,
        )
    }

    override fun onKoinConfigurationFinish() {
        super.onKoinConfigurationFinish()
        RestInterceptors.addInterceptor(this)
    }

    override suspend fun onRequest(req: HttpRequestBuilder) {
        when (val it = useCaseGetAuthToken.getToken().value) {
            None -> {}
            is Some -> { req.header(HttpHeaders.Authorization, "Bearer ${it.value}") }
        }
    }
}