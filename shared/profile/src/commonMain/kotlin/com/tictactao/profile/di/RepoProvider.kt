package com.tictactao.profile.di

import com.tictactao.profile.data.repo.ProfileRepoImpl
import com.tictactao.profile.domain.repo.ProfileRepo
import org.koin.dsl.module

val RepoProvider = module {
    single<ProfileRepo> {
        ProfileRepoImpl(
            profileDao = get()
        )
    }
}