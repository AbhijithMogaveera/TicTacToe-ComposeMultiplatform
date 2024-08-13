package com.shared.feature_profile.di

import com.shared.feature_profile.domain.use_case.UseCaseGetProfileDetails
import com.shared.feature_profile.domain.use_case.UseCaseSyncProfileDetailsWIthServer
import com.shared.feature_profile.domain.use_case.UseCaseUpdateProfileDetails
import org.koin.dsl.module

val ProfileUseCaseProvider = module {
    single<UseCaseGetProfileDetails> {
        UseCaseGetProfileDetails(
            profileRepo = get()
        )
    }
    single<UseCaseSyncProfileDetailsWIthServer> {
        UseCaseSyncProfileDetailsWIthServer(
            useCaseGetAuthToken = get(),
            appScope = get(),
            profileRepo = get()
        )
    }
    single<UseCaseUpdateProfileDetails> {
        UseCaseUpdateProfileDetails(
            profileRepo = get()
        )
    }
}