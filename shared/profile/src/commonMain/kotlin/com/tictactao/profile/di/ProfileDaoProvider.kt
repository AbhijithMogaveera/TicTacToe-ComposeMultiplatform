package com.tictactao.profile.di

import com.tictactao.profile.data.dao.ProfileDao
import org.koin.dsl.module

val ProfileDaoProvider = module{
    single<ProfileDao> {
        ProfileDao()
    }
}