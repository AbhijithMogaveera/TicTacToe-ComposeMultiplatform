package com.shared.compose_foundation.platform

expect fun getPlatformName():String

object Platform{
    val isAndroid = getPlatformName() == "ANDROID"
    val isIphone = getPlatformName() == "IOS"
}

fun getHostAddress() = if (Platform.isIphone) {
    "127.0.0.1"
} else {
    "10.0.2.2"
}
