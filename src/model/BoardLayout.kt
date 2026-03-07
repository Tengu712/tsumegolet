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
    return BoardLayout(
        cols = cols,
        rows = rows,
        left = if (kifuData.offsetCol > 0) 0f else 0.5f,
        right = if (kifuData.offsetCol + cols < kifuData.boardCols) cols.toFloat() else cols - 0.5f,
        top = if (kifuData.offsetRow + rows < kifuData.boardRows) 0f else 0.5f,
        bottom = if (kifuData.offsetRow > 0) rows.toFloat() else rows - 0.5f,
    )
}
