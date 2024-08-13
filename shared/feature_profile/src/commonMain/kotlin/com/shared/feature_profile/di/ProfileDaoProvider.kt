package com.shared.feature_profile.di

import com.shared.feature_profile.data.dao.ProfileDao
import org.koin.dsl.module

val ProfileDaoProvider = module {
    single<ProfileDao> {
        ProfileDao()
    }
}