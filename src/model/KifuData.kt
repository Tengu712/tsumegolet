package com.skdassoc.tsumegolet.model

data class KifuData(
    val title: String,

    // 碁盤のサイズ
    val boardCols: Int,
    val boardRows: Int,

    // 碁盤のクリップ領域
    val offsetCol: Int = 0,
    val offsetRow: Int = 0,
    val extentCols: Int = boardCols,
    val extentRows: Int = boardRows,
)
