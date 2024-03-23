package com.tictactao.profile.di

import com.tictactao.profile.domain.use_case.UseCaseGetProfileDetails
import com.tictactao.profile.domain.use_case.UseCaseSyncProfileDetailsWIthServer
import com.tictactao.profile.domain.use_case.UseCaseUpdateProfileDetails
import org.koin.dsl.module

val ProfileUseCaseProvider = module {
    single<UseCaseGetProfileDetails> {
        UseCaseGetProfileDetails(
            profileRepo = get(),
            useCaseGetAuthToken = get(),
            appScope = get()
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