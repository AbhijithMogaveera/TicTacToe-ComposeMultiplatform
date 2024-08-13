package com.shared.feature_profile.di

import com.shared.feature_profile.data.repo.ProfileRepoImpl
import com.shared.feature_profile.domain.repo.ProfileRepo
import org.koin.dsl.module

val RepoProvider = module {
    single<ProfileRepo> {
        ProfileRepoImpl(
            profileDao = get()
        )
    }
}