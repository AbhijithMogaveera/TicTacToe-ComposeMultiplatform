package com.shared.profile

import com.shared.compose_foundation.module_config.ModuleConfiguration
import com.shared.compose_foundation.usecase.SyncUseCase
import com.shared.profile.di.ProfileDaoProvider
import com.shared.profile.di.ProfileUseCaseProvider
import com.shared.profile.di.ProfileViewModelProvider
import com.shared.profile.di.RepoProvider
import com.shared.profile.domain.use_case.UseCaseSyncProfileDetailsWIthServer
import org.koin.core.KoinApplication
import org.koin.core.component.inject

object FeatureProfileConfiguration : ModuleConfiguration {

    private val useCase: UseCaseSyncProfileDetailsWIthServer by inject()

    override fun configKoinModules(koinApplication: KoinApplication) {
        koinApplication.modules(ProfileUseCaseProvider)
        koinApplication.modules(ProfileViewModelProvider)
        koinApplication.modules(RepoProvider)
        koinApplication.modules(ProfileDaoProvider)
    }

    override fun onKoinConfigurationFinish() {
        super.onKoinConfigurationFinish()
        startSync()
    }

    override fun getSyncUseCases(): List<SyncUseCase> {
        return listOf(
            useCase
        )
    }

}