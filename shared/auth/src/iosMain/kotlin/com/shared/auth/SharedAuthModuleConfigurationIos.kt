package com.shared.auth

import org.koin.core.KoinApplication

object SharedAuthModuleConfigurationIos: SharedAuthModuleConfiguration() {
    override fun configKoinModules(koinApplication: KoinApplication) {
        super.configKoinModules(koinApplication)
    }
}