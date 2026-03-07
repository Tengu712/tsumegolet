package com.skdassoc.tsumegolet.model

data class BoardLayout(
    val cols: Int,
    val rows: Int,
    val left: Float,
    val right: Float,
    val top: Float,
    val bottom: Float,
)

fun computeBoardLayout(kifuData: KifuData): BoardLayout {
    val cols = kifuData.extentCols
    val rows = kifuData.extentRows

    val boardSize = kifuData.boardSize.value

    val left = if (kifuData.offsetCol > 0) 0f else 0.5f
    val right = if (kifuData.offsetCol + cols < boardSize) cols.toFloat() else cols - 0.5f
    val top = if (kifuData.offsetRow + rows < boardSize) 0f else 0.5f
    val bottom = if (kifuData.offsetRow > 0) rows.toFloat() else rows - 0.5f

    return BoardLayout(cols, rows, left, right, top, bottom)
}
