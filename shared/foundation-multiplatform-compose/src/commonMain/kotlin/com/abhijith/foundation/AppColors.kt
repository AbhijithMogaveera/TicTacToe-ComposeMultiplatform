package com.abhijith.foundation

import androidx.compose.ui.graphics.Color

object AppColors {
    val BACKGROUND = Color("#040D12".toColorInt())
    val CONTAINER = Color("#183D3D".toColorInt())
    val CONTAINER_MEDIUM = Color("#5C8374".toColorInt())
    val CONTAINER_SMALL = Color("#93B1A6".toColorInt())
}

fun String.toColorInt(): Int {
    if (this[0] == '#') {
        var color = substring(1).toLong(16)
        if (length == 7) {
            color = color or 0x00000000ff000000L
        } else if (length != 9) {
            throw IllegalArgumentException("Unknown color")
        }
        return color.toInt()
    }
    throw IllegalArgumentException("Unknown color")
}