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
