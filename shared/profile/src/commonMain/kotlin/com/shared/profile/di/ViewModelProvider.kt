package com.shared.profile.di

import com.shared.profile.domain.ProfileViewModel
import org.koin.dsl.module

val ProfileViewModelProvider = module{
    factory <ProfileViewModel>{
        ProfileViewModel(
            useCaseGetProfileDetails = get(),
            useCaseUpdateProfileDetails = get(),
            userCaseLogout = get()
        )
    }
}