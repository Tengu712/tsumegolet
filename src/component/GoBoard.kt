package com.skdassoc.tsumegolet.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.model.StoneColor
import com.skdassoc.tsumegolet.model.computeBoardLayout

private val starSize = 0.15f
private val stoneSize = 0.5f

@Composable
fun GoBoard(kifu: KifuData, maxWidth: Dp, maxHeight: Dp) {
    val layout = computeBoardLayout(kifu)
    val cellSize = minOf(maxWidth / layout.cols, maxHeight / layout.rows)

    Canvas(
        modifier = Modifier.padding(12.dp).size(cellSize * layout.cols, cellSize * layout.rows)
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
            val x = col * cell + cell / 2
            val y = row * cell + cell / 2
            drawCircle(Color.Black, radius = cell * starSize, center = Offset(x, y))
        }

        for (stone in layout.stones) {
            val x = stone.col * cell + cell / 2
            val y = stone.row * cell + cell / 2
            val radius = cell * stoneSize
            when (stone.color) {
                StoneColor.Black -> drawCircle(Color.Black, radius = radius, center = Offset(x, y))
                StoneColor.White -> {
                    drawCircle(Color.White, radius = radius, center = Offset(x, y))
                    drawCircle(
                        Color.Black,
                        radius = radius,
                        center = Offset(x, y),
                        style = Stroke(width = stroke),
                    )
                }
            }
        }
    }
}
