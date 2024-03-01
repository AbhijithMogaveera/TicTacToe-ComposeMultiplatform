package com.abhijith.foundation.koin

import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent

interface KoinConfig: KoinComponent {
    fun configKoinModules(
        koinApplication: KoinApplication,
    )
    fun onKoinConfigurationFinish(){}
}