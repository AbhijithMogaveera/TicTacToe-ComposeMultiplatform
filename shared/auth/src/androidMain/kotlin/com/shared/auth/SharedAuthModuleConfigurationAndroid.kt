package com.shared.auth

import com.shared.auth.di.AndroidViewModelProvider
import org.koin.core.KoinApplication

object SharedAuthModuleConfigurationAndroid: SharedAuthModuleConfiguration() {
    override fun configKoinModules(koinApplication: KoinApplication) {
        super.configKoinModules(koinApplication)
        koinApplication.modules(AndroidViewModelProvider)
    }
}