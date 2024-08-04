package com.example.kmmsample.android

import android.app.Application
import com.shared.compose_foundation.platform.KMMContextProvider
import com.shared.configModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        KMMContextProvider.setApplication(application = this)
        startKoin {
            androidLogger()
            androidContext(this@App)
            configModules()
        }
    }
}