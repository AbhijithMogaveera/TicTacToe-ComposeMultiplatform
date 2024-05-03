package com.shared.tic_tac_toe

import com.shared.compose_foundation.module_config.ModuleConfiguration
import com.shared.tic_tac_toe.di.UseCaseProvider
import com.shared.tic_tac_toe.di.ViewModelProvider
import org.koin.core.KoinApplication

object TicTacToeConfiguration:ModuleConfiguration {
    override fun configKoinModules(koinApplication: KoinApplication) {
        koinApplication.modules(ViewModelProvider)
        koinApplication.modules(UseCaseProvider)
    }
}