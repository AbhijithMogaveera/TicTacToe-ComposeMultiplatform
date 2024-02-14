package com.abhijith.foundation.koin

import org.koin.core.KoinApplication
import org.koin.core.module.Module

interface KoinConfig {
    fun configKoinModules(koinApplication: KoinApplication)
}