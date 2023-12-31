package com.example.kmmsample

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform