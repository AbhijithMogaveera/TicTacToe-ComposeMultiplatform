package com.abhijith.auth

import com.abhijith.auth.di.IosViewModelProvider
import com.abhijith.auth.di.UseCaseProvider
import org.koin.core.KoinApplication

object SharedAuthModuleConfigIos: SharedAuthModuleConfig() {
    override fun configKoinModules(koinApplication: KoinApplication) {
        super.configKoinModules(koinApplication)
        koinApplication.modules(IosViewModelProvider)
    }
}