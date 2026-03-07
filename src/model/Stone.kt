package com.skdassoc.tsumegolet.model

enum class StoneColor {
    Black,
    White,
}

data class Stone(val col: Int, val row: Int, val color: StoneColor)
