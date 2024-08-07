package com.shared.profile.di

import com.shared.profile.data.dao.ProfileDao
import org.koin.dsl.module

val ProfileDaoProvider = module {
    single<ProfileDao> {
        ProfileDao()
    }
}