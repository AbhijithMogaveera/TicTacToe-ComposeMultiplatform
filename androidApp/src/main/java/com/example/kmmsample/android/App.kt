package com.example.kmmsample.android

import android.app.Application
import com.shared.auth.AndroidAuthModuleConfiguration
import com.shared.compose_foundation.SharedFoundationConfiguration
import com.shared.compose_foundation.platform.KMMContextProvider
import com.shared.tic_tac_toe.TicTacToeConfiguration
import com.shared.profile.ProfileModuleConfiguration
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.KoinApplication
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KMMContextProvider.setApplication(application = this)
        startKoin {
            androidLogger()
            androidContext(this@App)
            configModules()
        }
    }

    private fun KoinApplication.configModules() {
        listOf(
            AndroidAuthModuleConfiguration,
            SharedFoundationConfiguration,
            TicTacToeConfiguration,
            ProfileModuleConfiguration
        ).onEach {
            it.configKoinModules(this)
        }.onEach {
            it.onKoinConfigurationFinish()
        }
    }

}