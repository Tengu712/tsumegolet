package com.skdassoc.tsumegolet.model

import org.junit.Assert.assertEquals
import org.junit.Test

class CanvasToBoardTest {
    @Test
    fun キャンバス座標から盤座標に変換される() {
        val kifu =
            KifuData(
                "",
                boardSize = BoardSize.Nineteen,
                offsetCol = 3,
                offsetRow = 2,
                extentCols = 5,
                extentRows = 6,
            )
        assertEquals(Pair(5, 4), convertCoordFromCanvasToBoard(kifu, 2, 3))
    }

    @Test
    fun オフセットなしの変換() {
        val kifu = KifuData("", boardSize = BoardSize.Nine)
        assertEquals(Pair(2, 2), convertCoordFromCanvasToBoard(kifu, 2, 6))
    }
}
