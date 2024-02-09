package com.abhijith.foundation

import com.abhijith.foundation.coroutines.CoroutineScopeProvider
import com.abhijith.foundation.module_config.ModuleConfig
import com.abhijith.foundation.prefrence.PreferenceProvide
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