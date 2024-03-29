package com.abhijith.tic_tac_toe.ui.components

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.abhijith.tic_tac_toe.ui.components.toColorInt

val ActivePlayerIndicatorColor = Color("#FF407D".toColorInt())
val InActivePlayerIndicatorColor = Color.White.copy(alpha = 0.8f)
val activeStrokeWidth: Dp = 4.dp
val inActiveStrokeWidth: Dp = 2.dp
val borderColor = Color("#DDE6ED".toColorInt())
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