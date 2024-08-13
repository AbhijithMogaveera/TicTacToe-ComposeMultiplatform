package com.shared

import com.shared.auth.FeatureAuthInitializer
import com.shared.auth.util.AuthManager
import com.shared.compose_foundation.FoundationModuleInitilizer
import com.shared.compose_foundation.StartUpTask
import com.shared.feature_profile.FeatureProfileModuleConfiguration
import com.shared.feature_profile.domain.use_case.UseCaseSyncProfileDetailsWIthServer
import com.shared.tic_tac_toe.FeatureTicTacToeConfiguration
import org.koin.core.KoinApplication
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

fun KoinApplication.configCommonModules() {
    setUpDI()
    StartUpHandler().onStart()
}

private fun KoinApplication.setUpDI() {
    listOf(
        FeatureAuthInitializer,
        FoundationModuleInitilizer,
        FeatureProfileModuleConfiguration,
        FeatureTicTacToeConfiguration,
    ).onEach {
        it.configKoinModules(this)
    }.onEach {
        it.onKoinConfigurationFinish()
    }
}

class StartUpHandler : KoinComponent {
    fun onStart() {
        val startUpTasks: List<StartUpTask> = getStartUpTasks()
        startUpTasks.forEach { task -> task.execute() }
    }

    private fun getStartUpTasks(): List<StartUpTask> {
        val profileDetailsSync: UseCaseSyncProfileDetailsWIthServer by inject()
        val accountsManager: AuthManager by inject()
        return listOf(profileDetailsSync, accountsManager)
    }
}
