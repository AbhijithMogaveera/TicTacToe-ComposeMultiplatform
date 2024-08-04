package com.shared.compose_foundation.platform

import android.app.Application

actual object KMMContextProvider {

    private var context: Kontext? = null

    actual fun getKontext(): Kontext {
       return context?: throw IllegalStateException("Application not set!")
    }

    fun setApplication(application: Application){
        context = application
    }

    fun clearApplication(){
        context = null
    }
}