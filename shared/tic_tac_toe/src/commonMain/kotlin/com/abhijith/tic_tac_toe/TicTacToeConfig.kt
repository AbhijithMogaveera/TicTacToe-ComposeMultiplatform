package com.abhijith.tic_tac_toe

import com.abhijith.foundation.module_config.ModuleConfig
import com.abhijith.tic_tac_toe.di.ViewModelProvider
import org.koin.core.KoinApplication

object TicTacToeConfig:ModuleConfig {
    override fun configKoinModules(koinApplication: KoinApplication) {
        koinApplication.modules(ViewModelProvider)
    }
}