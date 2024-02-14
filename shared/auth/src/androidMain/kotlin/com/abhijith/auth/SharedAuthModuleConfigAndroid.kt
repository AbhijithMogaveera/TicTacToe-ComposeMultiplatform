package com.abhijith.auth

import com.abhijith.auth.di.ViewModelProvider
import com.abhijith.foundation.module_config.ModuleConfig
import org.koin.core.KoinApplication

object SharedAuthModuleConfigAndroid: SharedAuthModuleConfig() {
    override fun configKoinModules(koinApplication: KoinApplication) {
        super.configKoinModules(koinApplication)
        koinApplication.modules(ViewModelProvider)
    }
}