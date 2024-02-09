package com.example.auth

import com.abhijith.auth.SharedAuthModuleConfigAndroid
import com.abhijith.foundation.module_config.ModuleConfig
import com.abhijith.auth.di.AuthApiProvider
import com.abhijith.auth.di.UseCaseProvider
import com.abhijith.auth.di.UtilityProvider
import com.abhijith.auth.di.ViewModelProvider
import org.koin.core.KoinApplication

object AuthModuleConfig : ModuleConfig {
    override fun configKoinModules(koinApplication: KoinApplication) {
        SharedAuthModuleConfigAndroid.configKoinModules(koinApplication)
    }
}
