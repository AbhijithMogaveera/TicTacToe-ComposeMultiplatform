package com.example.kmmsample.android

import android.app.Application
import com.abhijith.auth.AndroidAuthModuleConfig
import com.abhijith.foundation.SharedFoundationConfig
import com.abhijith.foundation.platform.KMMContextProvider
import com.abhijith.tic_tac_toe.TicTacToeConfig
import com.tictactao.profile.ProfileModuleConfig
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
            AndroidAuthModuleConfig,
            SharedFoundationConfig,
            TicTacToeConfig,
            ProfileModuleConfig
        ).onEach {
            it.configKoinModules(this)
        }.onEach {
            it.onKoinConfigurationFinish()
        }
    }

}