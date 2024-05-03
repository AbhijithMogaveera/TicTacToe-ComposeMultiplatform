package com.ttt

class Greeting {
    private val platform: Platform = com.ttt.getPlatform()

    fun greet(): String {
        return "Hello, ${platform.name}!"
    }
}