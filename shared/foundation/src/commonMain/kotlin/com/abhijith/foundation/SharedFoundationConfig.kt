package com.abhijith.foundation

import com.abhijith.foundation.coroutines.di.CoroutineScopeProvider
import com.abhijith.foundation.module_config.ModuleConfig
import com.abhijith.foundation.prefrence.di.PreferenceProvide
import org.koin.core.KoinApplication

object SharedFoundationConfig : ModuleConfig {
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