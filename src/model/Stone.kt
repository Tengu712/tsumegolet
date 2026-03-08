package com.skdassoc.tsumegolet.model

enum class StoneColor {
    Black,
    White,
}

val StoneColor.displayName: String
    get() =
        when (this) {
            StoneColor.Black -> "黒"
            StoneColor.White -> "白"
        }

data class Stone(val col: Int, val row: Int, val color: StoneColor)
