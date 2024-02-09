package com.abhijith.foundation.prefrence

import com.abhijith.foundation.platform.KMMContextProvider
import org.koin.dsl.module

val PreferenceProvide = module {
    single<KmmPreference> {
        KMMPreferenceImpl(KMMContextProvider.getKontext())
    }
}