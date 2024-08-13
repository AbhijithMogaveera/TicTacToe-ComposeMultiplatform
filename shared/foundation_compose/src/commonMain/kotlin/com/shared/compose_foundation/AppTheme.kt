package com.shared.compose_foundation

import androidx.compose.material3.AssistChipDefaults
import androidx.compose.material3.ChipColors
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object AppTheme {
    val White: Color = Color.White
    val Background = Color(0xff040D12)
    val Container = Color(0xff183D3D)
    val ContainerMedium = Color(0xff5C8374)
    val ContainerSmall = Color(0xff93B1A6)

    val colorScheme = darkColorScheme(
        primary = ContainerSmall,
        onPrimary = Color.White,
        secondary = ContainerMedium,
        onSecondary = Color.White,
        background = Background,
        onBackground = Color.White,
        surface = Container,
        onSurface = Color.White,
        onPrimaryContainer = Color.Black
    )
    val defaultTextFieldTextStyle = TextStyle(
        color = Color.White,
        fontWeight = FontWeight.W400,
        fontSize = 18.sp
    )
    val defaultTextFieldColors
        @Composable get() = TextFieldDefaults.colors(
            focusedContainerColor = AppTheme.Background.copy(alpha = 0.5f),
            unfocusedContainerColor = AppTheme.Background.copy(alpha = 0.5f),
        )
    val DefaultAssistChipColor: ChipColors
        @Composable get() = AssistChipDefaults.assistChipColors(
            containerColor = AppTheme.ContainerMedium,
            labelColor = Color.White
        )

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