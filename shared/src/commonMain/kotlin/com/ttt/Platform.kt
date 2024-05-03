package com.ttt

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform