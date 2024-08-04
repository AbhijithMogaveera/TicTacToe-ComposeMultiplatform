package com.shared.compose_foundation.prefrence.di

import com.shared.compose_foundation.platform.KMMContextProvider
import com.shared.compose_foundation.prefrence.KMMPreferenceImpl
import com.shared.compose_foundation.prefrence.KmmPreference
import org.koin.dsl.module

val PreferenceProvide = module {
    single<KmmPreference> {
        KMMPreferenceImpl(KMMContextProvider.getKontext())
    }
}