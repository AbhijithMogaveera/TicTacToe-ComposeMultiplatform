package com.abhijith.foundation.module_config

import org.koin.core.KoinApplication
import org.koin.core.module.Module

interface KoinConfig {
    fun configKoinModules(koinApplication: KoinApplication)
}