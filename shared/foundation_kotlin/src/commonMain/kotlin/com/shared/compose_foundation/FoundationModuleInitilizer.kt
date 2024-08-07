package com.shared.compose_foundation

import com.shared.compose_foundation.coroutines.di.CoroutineScopeProvider
import com.shared.compose_foundation.koin.ModuleInitilizer
import com.shared.compose_foundation.prefrence.di.PreferenceProvide
import org.koin.core.KoinApplication

 object FoundationModuleInitilizer : ModuleInitilizer {
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