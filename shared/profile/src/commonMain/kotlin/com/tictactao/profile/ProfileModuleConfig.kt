package com.tictactao.profile

import com.abhijith.foundation.module_config.ModuleConfig
import com.abhijith.foundation.usecase.SyncUseCase
import com.tictactao.profile.di.ProfileDaoProvider
import com.tictactao.profile.di.ProfileUseCaseProvider
import com.tictactao.profile.di.ProfileViewModelProvider
import com.tictactao.profile.di.RepoProvider
import com.tictactao.profile.domain.use_case.UseCaseSyncProfileDetailsWIthServer
import org.koin.core.KoinApplication
import org.koin.core.component.inject

object ProfileModuleConfig : ModuleConfig {

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