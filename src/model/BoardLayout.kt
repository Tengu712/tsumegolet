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

fun convertStonesFromBoardToCanvas(kifu: KifuData, stones: List<Stone>): List<CanvasStone> =
    stones
        .filter { isInCanvas(kifu, it.col, it.row) }
        .map {
            val (c, r) = convertCoordFromBoardToCanvas(kifu, it.col, it.row)
            CanvasStone(c, r, it.color)
        }

fun convertCoordFromCanvasToBoard(kifu: KifuData, col: Int, row: Int): Pair<Int, Int> =
    Pair(col + kifu.offsetCol, kifu.extentRows - 1 - row + kifu.offsetRow)

fun computeBoardLayout(kifu: KifuData): BoardLayout {
    val cols = kifu.extentCols
    val rows = kifu.extentRows
    val boardSize = kifu.boardSize.value

    val stars =
        boardStars(kifu.boardSize)
            .filter { (col, row) -> isInCanvas(kifu, col, row) }
            .map { (col, row) -> convertCoordFromBoardToCanvas(kifu, col, row) }

    return BoardLayout(
        cols = cols,
        rows = rows,
        left = if (kifu.offsetCol > 0) 0f else 0.5f,
        right = if (kifu.offsetCol + cols < boardSize) cols.toFloat() else cols - 0.5f,
        top = if (kifu.offsetRow + rows < boardSize) 0f else 0.5f,
        bottom = if (kifu.offsetRow > 0) rows.toFloat() else rows - 0.5f,
        stars = stars,
    )
}

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

private fun isInCanvas(kifu: KifuData, col: Int, row: Int): Boolean =
    col >= kifu.offsetCol &&
        col < kifu.offsetCol + kifu.extentCols &&
        row >= kifu.offsetRow &&
        row < kifu.offsetRow + kifu.extentRows

private fun convertCoordFromBoardToCanvas(kifu: KifuData, col: Int, row: Int): Pair<Int, Int> =
    Pair(col - kifu.offsetCol, kifu.extentRows - 1 - (row - kifu.offsetRow))
