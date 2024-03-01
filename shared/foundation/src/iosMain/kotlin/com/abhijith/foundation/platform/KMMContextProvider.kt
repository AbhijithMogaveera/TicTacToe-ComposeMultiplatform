package com.abhijith.foundation.platform

import platform.darwin.NSObject

actual object KMMContextProvider {

    private var app: NSObject? = null

    actual fun getKontext(): Kontext {
        return app ?: throw IllegalStateException("Application not set!")
    }

    fun setNSObject(app: NSObject) {
        this.app = app
    }

    fun clearApplication() {
        app = null
    }

}