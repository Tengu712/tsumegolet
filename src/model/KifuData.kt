package com.skdassoc.tsumegolet.model

data class KifuData(
    val title: String,
    val description: String = "",

    // 碁盤のサイズ
    val boardSize: BoardSize = BoardSize.Nineteen,

    // 碁盤のクリップ領域
    val offsetCol: Int = 0,
    val offsetRow: Int = 0,
    val extentCols: Int = boardSize.value,
    val extentRows: Int = boardSize.value,
    val stones: List<Stone> = emptyList(),

    // 正解の手
    val answerCol: Int = 0,
    val answerRow: Int = 0,
    val answerTurn: StoneColor = StoneColor.Black,
)
