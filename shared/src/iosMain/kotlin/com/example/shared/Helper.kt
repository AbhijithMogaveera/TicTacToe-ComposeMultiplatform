package com.example.shared

import com.abhijith.auth.SharedAuthModuleConfigIos
import com.abhijith.foundation.SharedFoundationConfig
import com.abhijith.foundation.platform.KMMContextProvider
import com.abhijith.tic_tac_toe.TicTacToeConfig
import org.koin.core.context.startKoin
import platform.Foundation.NSUserDefaults

class KMMIosApplication constructor() {

    val modules = mutableListOf(
        SharedFoundationConfig,
        SharedAuthModuleConfigIos,
        TicTacToeConfig
    )

    fun initApp() {
        startKoin {
            KMMContextProvider.setNSObject(
                NSUserDefaults.standardUserDefaults()
            )
            modules.forEach {
                it.configKoinModules(this)
            }
            modules.forEach {
                it.onKoinConfigurationFinish()
            }
        }
    }

}