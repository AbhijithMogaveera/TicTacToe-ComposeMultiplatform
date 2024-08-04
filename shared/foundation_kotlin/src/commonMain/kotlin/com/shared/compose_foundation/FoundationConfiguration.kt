package com.shared.compose_foundation

import com.shared.compose_foundation.coroutines.di.CoroutineScopeProvider
import com.shared.compose_foundation.module_config.ModuleConfiguration
import com.shared.compose_foundation.prefrence.di.PreferenceProvide
import org.koin.core.KoinApplication

public object FoundationConfiguration : ModuleConfiguration {
    override fun configKoinModules(
        koinApplication: KoinApplication
    ) {
        koinApplication
            .modules(
                PreferenceProvide,
                CoroutineScopeProvider
            )
    }
}