package com.abhijith.auth

import com.abhijith.auth.di.AuthApiProvider
import com.abhijith.auth.di.UseCaseProvider
import com.abhijith.auth.di.UtilityProvider
import com.abhijith.foundation.module_config.ModuleConfig
import org.koin.core.KoinApplication

object SharedAuthModuleConfigCommon:ModuleConfig {
    override fun configKoinModules(koinApplication: KoinApplication) {
        koinApplication.modules(
            UseCaseProvider,
            AuthApiProvider,
            UtilityProvider
        )
    }
}