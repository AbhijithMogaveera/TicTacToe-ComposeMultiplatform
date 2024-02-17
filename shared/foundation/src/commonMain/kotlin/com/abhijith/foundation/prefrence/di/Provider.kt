package com.abhijith.foundation.prefrence.di

import com.abhijith.foundation.platform.KMMContextProvider
import com.abhijith.foundation.prefrence.KMMPreferenceImpl
import com.abhijith.foundation.prefrence.KmmPreference
import org.koin.dsl.module

val PreferenceProvide = module {
    single<KmmPreference> {
        KMMPreferenceImpl(KMMContextProvider.getKontext())
    }
}