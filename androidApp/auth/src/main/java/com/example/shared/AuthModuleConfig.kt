package com.example.shared

import com.abhijith.auth.SharedAuthModuleConfigAndroid
import com.abhijith.foundation.module_config.ModuleConfig
import org.koin.core.KoinApplication

object AuthModuleConfig : ModuleConfig {
    override fun configKoinModules(koinApplication: KoinApplication) {
        SharedAuthModuleConfigAndroid.configKoinModules(koinApplication)
    }
}
