package com.skdassoc.tsumegolet.component

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.model.computeBoardLayout

private val boardSize = 0.9f

@Composable
fun GoBoard(kifu: KifuData, maxWidth: Dp) {
    val layout = computeBoardLayout(kifu)
    val cellSize = maxWidth * boardSize / layout.cols

    Canvas(modifier = Modifier.size(cellSize * layout.cols, cellSize * layout.rows)) {
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
    }
}
