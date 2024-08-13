package com.shared.auth

import arrow.core.None
import arrow.core.Some
import com.shared.auth.di.AuthApiProvider
import com.shared.auth.di.UseCaseProvider
import com.shared.auth.di.AccountManagerProvider
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

/**
 * Singleton object responsible for initializing Koin modules related to authentication
 * and handling HTTP request interception for authorization tokens.
 *
 * Implements [ModuleInitializer] to configure Koin modules and [RequestResponseInterceptor]
 * to modify HTTP requests with authorization tokens.
 *
 * This object should be used to set up authentication-related dependencies and ensure
 * that HTTP requests include the necessary authorization headers.
 *
 * Example usage:
 * ```
 * FeatureAuthInitializer.configKoinModules(koinApplication)
 * ```
 */
object FeatureAuthInitializer : ModuleInitilizer, RequestResponseInterceptor {

    /**
     * Lazy-initialized use case for retrieving authentication tokens.
     *
     * This property is initialized when accessed and provides an instance of [UseCaseGetAuthToken].
     */
    private val useCaseGetAuthToken: UseCaseGetAuthToken by lazy { get() }

    /**
     * Configures Koin modules related to authentication.
     *
     * @param koinApplication The [KoinApplication] instance used to define and configure Koin modules.
     *
     * This method sets up the necessary Koin modules for authentication, including providers
     * for use cases, API services, and account management.
     */
    override fun configKoinModules(koinApplication: KoinApplication) {
        koinApplication.modules(
            UseCaseProvider,
            AuthApiProvider,
            AccountManagerProvider
        )
    }

    /**
     * Called after Koin configuration is finished.
     *
     * This method adds the current object as an interceptor for handling authorization tokens
     * in HTTP requests by calling [RestInterceptors.addInterceptor].
     */
    override fun onKoinConfigurationFinish() {
        super.onKoinConfigurationFinish()
        RestInterceptors.addInterceptor(this)
    }

    /**
     * Intercepts HTTP requests to add an authorization token header.
     *
     * @param req The [HttpRequestBuilder] representing the HTTP request to be modified.
     *
     * This method retrieves the current authentication token using [useCaseGetAuthToken]
     * and, if available, adds it to the request headers as a Bearer token.
     */
    override suspend fun onRequest(req: HttpRequestBuilder) {
        when (val it = useCaseGetAuthToken.getToken().value) {
            None -> {}
            is Some -> { req.header(HttpHeaders.Authorization, "Bearer ${it.value}") }
        }
    }
}
