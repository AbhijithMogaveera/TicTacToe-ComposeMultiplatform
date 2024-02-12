package com.example.kmmsample.android

import android.app.Application
import com.abhijith.foundation.SharedFoundationConfig
import com.abhijith.foundation.module_config.ModuleConfig
import com.abhijith.foundation.platform.KMMContextProvider
import com.example.shared.AuthModuleConfig
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {

    private val moduleConfigs = mutableListOf<ModuleConfig>()

    override fun onCreate() {
        super.onCreate()
        moduleConfigs.add(AuthModuleConfig)
        moduleConfigs.add(SharedFoundationConfig)
        startKoin {
            androidLogger()
            androidContext(this@App)
            configModules()
        }
        KMMContextProvider.setApplication(application = this)
    }

    private fun KoinApplication.configModules() {
        moduleConfigs.forEach { it: ModuleConfig ->
            it.configKoinModules(koinApplication = this)
        }
    }

}