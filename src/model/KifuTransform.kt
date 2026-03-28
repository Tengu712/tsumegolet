package com.skdassoc.tsumegolet.model

fun transformKifu(kifu: KifuData, rotation: Int, swap: Boolean): KifuData {
    val n = kifu.boardSize.value

    fun rotateCoord(coord: BoardCoord): BoardCoord =
        when (rotation % 4) {
            1 -> BoardCoord(n - 1 - coord.row, coord.col)
            2 -> BoardCoord(n - 1 - coord.col, n - 1 - coord.row)
            3 -> BoardCoord(coord.row, n - 1 - coord.col)
            else -> coord
        }

    fun swapTurn(turn: StoneColor): StoneColor = if (swap) turn.flipped else turn

    val (oCol, oRow, eCols, eRows) =
        when (rotation % 4) {
            1 ->
                listOf(
                    n - kifu.offsetRow - kifu.extentRows,
                    kifu.offsetCol,
                    kifu.extentRows,
                    kifu.extentCols,
                )
            2 ->
                listOf(
                    n - kifu.offsetCol - kifu.extentCols,
                    n - kifu.offsetRow - kifu.extentRows,
                    kifu.extentCols,
                    kifu.extentRows,
                )
            3 ->
                listOf(
                    kifu.offsetRow,
                    n - kifu.offsetCol - kifu.extentCols,
                    kifu.extentRows,
                    kifu.extentCols,
                )
            else -> listOf(kifu.offsetCol, kifu.offsetRow, kifu.extentCols, kifu.extentRows)
        }

    val stones = kifu.stones.map { Stone(rotateCoord(it.coord), swapTurn(it.color)) }

    return kifu.copy(
        offsetCol = oCol,
        offsetRow = oRow,
        extentCols = eCols,
        extentRows = eRows,
        stones = stones,
        answer = rotateCoord(kifu.answer),
        answerTurn = swapTurn(kifu.answerTurn),
    )
}
