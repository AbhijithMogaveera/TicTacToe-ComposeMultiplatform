package androidx.compose.material3.extension

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

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
@Composable
fun ButtonWithProgressbar(
    border: BorderStroke = BorderStroke(
        width = 2.dp,
        color = Color.White.copy(alpha = 0.7f)
    ),
    bgColor: Color = Color.Black,
    shape: Shape = RoundedCornerShape(20.dp),
    inProgress: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable() (RowScope.() -> Unit),
) {
    Box(
        modifier = modifier
            .clickable(onClick = onClick)
            .padding(10.dp)
            .clip(shape)
            .background(bgColor)
            .border(border, shape)
            .padding(10.dp)
    ) {
        Row(
            modifier = Modifier.align(Alignment.Center)
        ) {
            content()
        }
        if(inProgress) {
            CircularProgressIndicator(
                modifier = Modifier.align(Alignment.CenterEnd),
                color = Color.White
            )
        }
    }
}




