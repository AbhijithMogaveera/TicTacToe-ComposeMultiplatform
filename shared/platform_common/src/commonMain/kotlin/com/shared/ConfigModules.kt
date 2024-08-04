package com.shared

import com.shared.auth.FeatureAuthCongiguration
import com.shared.compose_foundation.FoundationConfiguration
import com.shared.profile.FeatureProfileConfiguration
import com.shared.tic_tac_toe.FeatureTicTacToeConfiguration
import org.koin.core.KoinApplication

fun KoinApplication.configModules() {
    listOf(
        FeatureAuthCongiguration,
        FoundationConfiguration,
        FeatureProfileConfiguration,
        FeatureTicTacToeConfiguration,
    ).onEach {
        it.configKoinModules(this)
    }.onEach {
        it.onKoinConfigurationFinish()
    }
}