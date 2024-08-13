package com.shared.feature_profile

import com.shared.compose_foundation.koin.ModuleInitilizer
import com.shared.feature_profile.di.ProfileDaoProvider
import com.shared.feature_profile.di.ProfileUseCaseProvider
import com.shared.feature_profile.di.RepoProvider
import org.koin.core.KoinApplication

object FeatureProfileModuleConfiguration : ModuleInitilizer {
    override fun configKoinModules(koinApplication: KoinApplication) {
        koinApplication.modules(ProfileUseCaseProvider)
        koinApplication.modules(RepoProvider)
        koinApplication.modules(ProfileDaoProvider)
    }
}