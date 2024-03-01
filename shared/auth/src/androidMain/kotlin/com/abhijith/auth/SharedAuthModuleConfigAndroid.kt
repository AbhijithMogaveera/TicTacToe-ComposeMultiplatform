package com.abhijith.auth

import com.abhijith.auth.di.AndroidViewModelProvider
import org.koin.core.KoinApplication

object SharedAuthModuleConfigAndroid: SharedAuthModuleConfig() {
    override fun configKoinModules(koinApplication: KoinApplication) {
        super.configKoinModules(koinApplication)
        koinApplication.modules(AndroidViewModelProvider)
    }
}