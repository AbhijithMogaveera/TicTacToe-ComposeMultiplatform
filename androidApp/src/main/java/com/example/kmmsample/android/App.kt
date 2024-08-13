package com.example.kmmsample.android

import android.app.Activity
import android.app.Application
import android.os.Bundle
import com.shared.compose_foundation.platform.KMMContextProvider
import com.shared.configCommonModules
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin

class App : Application(), Application.ActivityLifecycleCallbacks {
    val activities = mutableListOf<Activity>();
    override fun onCreate() {
        super.onCreate()
        KMMContextProvider.setApplication(application = this)
        startKoin {
            androidLogger()
            androidContext(this@App)
            configCommonModules()
        }
        registerActivityLifecycleCallbacks(this)
    }
    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
        activities.add(activity)
    }
    override fun onActivityStarted(activity: Activity) {}
    override fun onActivityResumed(activity: Activity) {}
    override fun onActivityPaused(activity: Activity) {}
    override fun onActivityStopped(activity: Activity) {}
    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {}
    override fun onActivityDestroyed(activity: Activity) {
        activities.remove(activity)
    }


}