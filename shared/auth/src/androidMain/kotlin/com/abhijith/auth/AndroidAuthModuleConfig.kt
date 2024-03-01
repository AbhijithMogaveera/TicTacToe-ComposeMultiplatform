package com.abhijith.auth

import com.abhijith.foundation.module_config.ModuleConfig
import org.koin.core.KoinApplication

object AndroidAuthModuleConfig : SharedAuthModuleConfig() {
    override fun configKoinModules(koinApplication: KoinApplication) {
        SharedAuthModuleConfigAndroid.configKoinModules(koinApplication)
    }
}
