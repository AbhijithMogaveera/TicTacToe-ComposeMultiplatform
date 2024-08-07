package com.shared.compose_foundation.koin

import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent

interface ModuleInitilizer: KoinComponent {
    fun configKoinModules(koinApplication: KoinApplication)
    fun onKoinConfigurationFinish(){}
}