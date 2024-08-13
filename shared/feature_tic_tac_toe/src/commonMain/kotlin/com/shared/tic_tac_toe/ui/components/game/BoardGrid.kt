package com.shared.tic_tac_toe.ui.components.game

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.AnimationVector1D
import androidx.compose.animation.core.EaseInOutQuad
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.scale
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.layout.LayoutCoordinates
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import arrow.core.None
import arrow.core.Option
import arrow.core.getOrElse
import arrow.core.some
import com.shared.compose_foundation.AppTheme
import com.shared.tic_tac_toe.domain.models.TileState
import com.shared.tic_tac_toe.domain.viewmodels.TicTacToeViewModel

@Composable
fun BoardGrid(modifier: Modifier) {
    CurrentUser().onSome { currentUser ->
        var tilesCoordinates by remember {
            mutableStateOf(listOf<Option<LayoutCoordinates>>(
                *(0..<9)
                    .map { None }
                    .toTypedArray()
            ))
        }
        var boardLayoutCoordinates: Option<LayoutCoordinates> by remember { mutableStateOf(None) }
        val boardState = TicTacToeViewModel.boardState.getOrNull() ?: return
        val animatedValue = remember {
            Animatable(0.7f)
        }

        LaunchedEffect(Unit) {
            animatedValue.animateTo(
                targetValue = 1f,
                animationSpec = infiniteRepeatable(
                    animation = tween(durationMillis = 600, easing = EaseInOutQuad),
                    repeatMode = RepeatMode.Reverse
                ),
            )
        }
        LazyVerticalGrid(

            columns = GridCells.Fixed(3),

            content = {
                items(count = 9) { index ->
                    Tile(
                        tileState = boardState.board[index],
                        modifier = Modifier.onGloballyPositioned { layoutCoordinates ->
                            tilesCoordinates = tilesCoordinates.mapIndexed { mIndex, option ->
                                if (mIndex == index)
                                    layoutCoordinates.some()
                                else
                                    option
                            }
                        }.clickable {
                            TicTacToeViewModel.onTileClick(index)
                        },
                        isFocused = boardState.winTileStart.isSome { it == index }
                                || boardState.winTileMiddle.isSome { it == index }
                                || boardState.winTileEnd.isSome { it == index },
                        nonStateScale = animatedValue
                    )
                }
            },
            modifier = Modifier
                .aspectRatio(1f)
                .fillMaxWidth()
                .drawBehind {
                    drawRoundRect(
                        color = AppTheme.Background,
                        cornerRadius = CornerRadius(60f, 60f)
                    )
                    if (currentUser.userName == boardState.currentTurnUsername) {
                        drawRoundRect(
                            color = AppTheme.ContainerSmall,
                            style = Stroke(
                                width = 4.dp.toPx(),
                            ),
                            cornerRadius = CornerRadius(60f, 60f)
                        )
                    }
                }

                .then(modifier)   .drawBehind {
                    (tilesCoordinates
                        .toList()
                        .takeIf { it.all { option -> option.isSome() } }
                        ?.mapNotNull { it.getOrNull() }
                        ?.some() ?: None)
                        .onSome { tilesCoordinates ->
                            val winTileStart =
                                boardState.winTileStart.getOrElse { return@drawBehind }
                            val winTileEnd =
                                boardState.winTileEnd.getOrElse { return@drawBehind }
                            val fromCoordinate = tilesCoordinates[winTileStart]
                            val toCoordinate = tilesCoordinates[winTileEnd]
                            boardLayoutCoordinates.onSome { parentLayoutCoordinates ->
                                drawLine(
                                    end = parentLayoutCoordinates.localPositionOf(
                                        sourceCoordinates = fromCoordinate,
                                        relativeToSource = Offset.Zero
                                    ).let {
                                        it.copy(
                                            it.x + toCoordinate.size.width / 2f,
                                            it.y + toCoordinate.size.height / 2f
                                        )
                                    },
                                    start = parentLayoutCoordinates.localPositionOf(
                                        sourceCoordinates = toCoordinate,
                                        relativeToSource = Offset.Zero
                                    ).let {
                                        it.copy(
                                            it.x + toCoordinate.size.width / 2f,
                                            it.y + toCoordinate.size.height / 2f
                                        )
                                    },
                                    strokeWidth = 100f,
                                    color = Color.Yellow,
                                    cap = StrokeCap.Round
                                )
                            }
                        }
                }.onGloballyPositioned {
                    boardLayoutCoordinates = it.some()
                }
        )
    }
}

@Composable
fun Tile(
    tileState: TileState,
    modifier: Modifier = Modifier,
    isFocused: Boolean,
    nonStateScale: Animatable<Float, AnimationVector1D>
) {
    Box(
        modifier = Modifier
            .aspectRatio(1 / 1f)
            .padding(10.dp)
            .then(modifier)
            .clip(CircleShape)
            .let {
                if (isFocused)
                    it.border(color = Color.Yellow, width = 5.dp, shape = CircleShape)
                else
                    it
            }
            .background(
                color = AppTheme.ContainerSmall.copy(
                    alpha = when (tileState) {
                        TileState.NONE -> 0.5f
                        TileState.O, TileState.X -> 1f
                    }
                )
            )
    ) {
        val x = when (tileState) {
            TileState.NONE -> TicTacToeViewModel.boardState.getOrNull()?.let {
                it.getPlayerByUserName(it.currentTurnUsername).tile.name
            } ?: ""

            TileState.O -> "O"
            TileState.X -> "X"
        }
        Text(
            x,
            modifier = Modifier.align(Alignment.Center).alpha(
                if (tileState == TileState.NONE) 0.3f else 1f
            ).scale(
                if (tileState == TileState.NONE) {
                    1 * nonStateScale.value
                } else {
                    1f
                }
            ),
            fontSize = 30.sp,
            fontWeight = FontWeight.Bold,
            color = AppTheme.Background
        )
    }
}