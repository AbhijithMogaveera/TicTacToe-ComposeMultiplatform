package com.shared.auth

import org.koin.core.KoinApplication

object AndroidAuthModuleConfiguration : SharedAuthModuleConfiguration() {
    override fun configKoinModules(koinApplication: KoinApplication) {
        SharedAuthModuleConfigurationAndroid.configKoinModules(koinApplication)
    }
}
