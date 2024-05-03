package com.shared.profile.di

import com.shared.profile.data.repo.ProfileRepoImpl
import com.shared.profile.domain.repo.ProfileRepo
import org.koin.dsl.module

val RepoProvider = module {
    single<ProfileRepo> {
        ProfileRepoImpl(
            profileDao = get()
        )
    }
}