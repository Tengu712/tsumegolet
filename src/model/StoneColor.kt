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

val StoneColor.flipped: StoneColor
    get() =
        when (this) {
            StoneColor.Black -> StoneColor.White
            StoneColor.White -> StoneColor.Black
        }
