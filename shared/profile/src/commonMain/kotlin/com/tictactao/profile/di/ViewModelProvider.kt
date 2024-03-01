package com.tictactao.profile.di

import com.tictactao.profile.domain.ProfileViewModel
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