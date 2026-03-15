package com.skdassoc.tsumegolet.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skdassoc.tsumegolet.model.BoardCoord
import com.skdassoc.tsumegolet.model.CanvasCoord
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.model.Stone
import com.skdassoc.tsumegolet.model.StoneColor
import com.skdassoc.tsumegolet.model.computeBoardLayout
import com.skdassoc.tsumegolet.model.convertCoordFromCanvasToBoard
import com.skdassoc.tsumegolet.model.convertStonesFromBoardToCanvas
import com.skdassoc.tsumegolet.model.displayName

private val starSize = 0.15f
private val stoneSize = 0.5f

private fun toPosition(cell: Float, coord: CanvasCoord): Offset =
    Offset(coord.col * cell + cell / 2, coord.row * cell + cell / 2)

@Composable
fun GoBoard(
    kifu: KifuData,
    stones: List<Stone>,
    turn: StoneColor,
    maxWidth: Dp,
    maxHeight: Dp,
    onTap: (BoardCoord) -> Unit,
) {
    val layout = computeBoardLayout(kifu)
    val stonesOnCanvas = convertStonesFromBoardToCanvas(kifu, stones)
    val cellSize = minOf(maxWidth / layout.cols, maxHeight / layout.rows)

    Column(modifier = Modifier.padding(12.dp)) {
        Canvas(
            modifier =
                Modifier.size(cellSize * layout.cols, cellSize * layout.rows).pointerInput(Unit) {
                    detectTapGestures { offset ->
                        val cell = size.width / layout.cols
                        val canvasCoord =
                            CanvasCoord((offset.x / cell).toInt(), (offset.y / cell).toInt())
                        onTap(convertCoordFromCanvasToBoard(kifu, canvasCoord))
                    }
                }
        ) {
            val cell = size.width / layout.cols
            val stroke = 1.dp.toPx()

            val l = layout.left * cell
            val r = layout.right * cell
            val t = layout.top * cell
            val b = layout.bottom * cell

            for (row in 0 until layout.rows) {
                val y = row * cell + cell / 2
                drawLine(Color.Black, Offset(l, y), Offset(r, y), stroke)
            }

            for (col in 0 until layout.cols) {
                val x = col * cell + cell / 2
                drawLine(Color.Black, Offset(x, t), Offset(x, b), stroke)
            }

            for (star in layout.stars) {
                drawCircle(Color.Black, radius = cell * starSize, center = toPosition(cell, star))
            }

            for (stone in stonesOnCanvas) {
                val pos = toPosition(cell, stone.coord)
                val radius = cell * stoneSize
                when (stone.color) {
                    StoneColor.Black -> drawCircle(Color.Black, radius = radius, center = pos)
                    StoneColor.White -> {
                        drawCircle(Color.White, radius = radius, center = pos)
                        drawCircle(
                            Color.Black,
                            radius = radius,
                            center = pos,
                            style = Stroke(width = stroke),
                        )
                    }
                }
            }
        }
        Text("手番：${turn.displayName}")
    }
}
