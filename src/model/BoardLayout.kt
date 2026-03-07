package com.skdassoc.tsumegolet.model

data class BoardLayout(
    val cols: Int,
    val rows: Int,
    val left: Float,
    val right: Float,
    val top: Float,
    val bottom: Float,
    val stars: List<Pair<Int, Int>>,
)

private fun boardStars(boardSize: BoardSize): List<Pair<Int, Int>> =
    when (boardSize) {
        BoardSize.Nine -> emptyList()
        BoardSize.Thirteen -> listOf(Pair(3, 3), Pair(3, 9), Pair(9, 3), Pair(9, 9), Pair(6, 6))
        BoardSize.Nineteen ->
            listOf(
                Pair(3, 3),
                Pair(3, 15),
                Pair(15, 3),
                Pair(15, 15),
                Pair(9, 9),
                Pair(9, 3),
                Pair(9, 15),
                Pair(3, 9),
                Pair(15, 9),
            )
    }

fun computeBoardLayout(kifuData: KifuData): BoardLayout {
    val cols = kifuData.extentCols
    val rows = kifuData.extentRows
    val boardSize = kifuData.boardSize.value

    val stars =
        boardStars(kifuData.boardSize)
            .filter { (boardCol, boardRow) ->
                boardCol >= kifuData.offsetCol &&
                    boardCol < kifuData.offsetCol + cols &&
                    boardRow >= kifuData.offsetRow &&
                    boardRow < kifuData.offsetRow + rows
            }
            .map { (boardCol, boardRow) ->
                val canvasCol = boardCol - kifuData.offsetCol
                val canvasRow = rows - 1 - (boardRow - kifuData.offsetRow)
                Pair(canvasCol, canvasRow)
            }

    return BoardLayout(
        cols = cols,
        rows = rows,
        left = if (kifuData.offsetCol > 0) 0f else 0.5f,
        right = if (kifuData.offsetCol + cols < boardSize) cols.toFloat() else cols - 0.5f,
        top = if (kifuData.offsetRow + rows < boardSize) 0f else 0.5f,
        bottom = if (kifuData.offsetRow > 0) rows.toFloat() else rows - 0.5f,
        stars = stars,
    )
}
