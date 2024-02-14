package com.example.shared

import com.abhijith.auth.SharedAuthModuleConfigIos
import com.abhijith.foundation.SharedFoundationConfig
import com.abhijith.foundation.platform.KMMContextProvider
import org.koin.core.context.startKoin
import platform.Foundation.NSUserDefaults

class KMMIosApplication constructor(){

    fun initApp() {
        startKoin {
            KMMContextProvider.setNSObject(
                NSUserDefaults.standardUserDefaults()
            )
            SharedFoundationConfig.configKoinModules(this)
            SharedAuthModuleConfigIos.configKoinModules(this)
        }
    }

}