package com.skdassoc.tsumegolet.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.gestures.detectTapGestures
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skdassoc.tsumegolet.model.BoardLayout
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.model.Stone
import com.skdassoc.tsumegolet.model.StoneColor
import com.skdassoc.tsumegolet.model.computeBoardLayout
import com.skdassoc.tsumegolet.model.convertCoordFromBoardToCanvas
import com.skdassoc.tsumegolet.model.displayName

private val starSize = 0.15f
private val stoneSize = 0.5f

private fun toPosition(cell: Float, col: Int, row: Int): Offset =
    Offset(col * cell + cell / 2, row * cell + cell / 2)

@Composable
fun GoBoard(kifu: KifuData, maxWidth: Dp, maxHeight: Dp) {
    val layout = computeBoardLayout(kifu)
    val cellSize = minOf(maxWidth / layout.cols, maxHeight / layout.rows)

    var answered by remember { mutableStateOf(false) }

    val (answerCanvasCol, answerCanvasRow) =
        convertCoordFromBoardToCanvas(kifu, kifu.answerCol, kifu.answerRow)

    Column(modifier = Modifier.padding(12.dp)) {
        GoBoardCanvas(
            layout = layout,
            cellSize = cellSize,
            extraStones =
                if (answered) listOf(Stone(answerCanvasCol, answerCanvasRow, kifu.answerTurn))
                else emptyList(),
            onTap = { col, row ->
                if (col == answerCanvasCol && row == answerCanvasRow) answered = true
            },
        )
        Text("手番：${kifu.answerTurn.displayName}")
    }
}

@Composable
private fun GoBoardCanvas(
    layout: BoardLayout,
    cellSize: Dp,
    extraStones: List<Stone>,
    onTap: (col: Int, row: Int) -> Unit,
) {
    Canvas(
        modifier =
            Modifier.size(cellSize * layout.cols, cellSize * layout.rows).pointerInput(Unit) {
                detectTapGestures { offset ->
                    val cell = size.width / layout.cols
                    onTap((offset.x / cell).toInt(), (offset.y / cell).toInt())
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

        for ((col, row) in layout.stars) {
            drawCircle(Color.Black, radius = cell * starSize, center = toPosition(cell, col, row))
        }

        for (stone in layout.stones + extraStones) {
            val pos = toPosition(cell, stone.col, stone.row)
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
}
