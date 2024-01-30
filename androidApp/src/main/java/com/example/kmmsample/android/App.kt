package com.example.kmmsample.android

import android.app.Application
import com.abhijith.foundation.platform.KMMContextProvider

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        KMMContextProvider.setApplication(application = this)
    }
}