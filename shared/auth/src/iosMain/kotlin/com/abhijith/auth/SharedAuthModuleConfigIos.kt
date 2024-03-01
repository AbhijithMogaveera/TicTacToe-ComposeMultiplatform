package com.abhijith.auth

import org.koin.core.KoinApplication

object SharedAuthModuleConfigIos: SharedAuthModuleConfig() {
    override fun configKoinModules(koinApplication: KoinApplication) {
        super.configKoinModules(koinApplication)
    }
}