package com.skdassoc.tsumegolet.model

data class BoardLayout(
    val cols: Int,
    val rows: Int,
    val left: Float,
    val right: Float,
    val top: Float,
    val bottom: Float,
    val stars: List<CanvasCoord>,
)

fun convertStonesFromBoardToCanvas(kifu: KifuData, stones: List<Stone>): List<CanvasStone> =
    stones
        .filter { isInCanvas(kifu, it.coord) }
        .map { CanvasStone(convertCoordFromBoardToCanvas(kifu, it.coord), it.color) }

fun convertCoordFromCanvasToBoard(kifu: KifuData, coord: CanvasCoord): BoardCoord =
    BoardCoord(coord.col + kifu.offsetCol, kifu.extentRows - 1 - coord.row + kifu.offsetRow)

fun computeBoardLayout(kifu: KifuData): BoardLayout {
    val cols = kifu.extentCols
    val rows = kifu.extentRows
    val boardSize = kifu.boardSize.value

    val stars =
        boardStars(kifu.boardSize)
            .filter { isInCanvas(kifu, it) }
            .map { convertCoordFromBoardToCanvas(kifu, it) }

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

private fun boardStars(boardSize: BoardSize): List<BoardCoord> =
    when (boardSize) {
        BoardSize.Nine -> emptyList()
        BoardSize.Thirteen ->
            listOf(
                BoardCoord(3, 3),
                BoardCoord(3, 9),
                BoardCoord(9, 3),
                BoardCoord(9, 9),
                BoardCoord(6, 6),
            )
        BoardSize.Nineteen ->
            listOf(
                BoardCoord(3, 3),
                BoardCoord(3, 15),
                BoardCoord(15, 3),
                BoardCoord(15, 15),
                BoardCoord(9, 9),
                BoardCoord(9, 3),
                BoardCoord(9, 15),
                BoardCoord(3, 9),
                BoardCoord(15, 9),
            )
    }

private fun isInCanvas(kifu: KifuData, coord: BoardCoord): Boolean =
    coord.col >= kifu.offsetCol &&
        coord.col < kifu.offsetCol + kifu.extentCols &&
        coord.row >= kifu.offsetRow &&
        coord.row < kifu.offsetRow + kifu.extentRows

private fun convertCoordFromBoardToCanvas(kifu: KifuData, coord: BoardCoord): CanvasCoord =
    CanvasCoord(coord.col - kifu.offsetCol, kifu.extentRows - 1 - (coord.row - kifu.offsetRow))
