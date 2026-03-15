package com.skdassoc.tsumegolet.model

import org.junit.Assert.assertEquals
import org.junit.Test

class StoneLayoutTest {
    @Test
    fun 盤座標が正しくキャンバス座標に変換される() {
        val kifu =
            KifuData(
                "",
                boardSize = BoardSize.Thirteen,
                offsetCol = 3,
                offsetRow = 2,
                extentCols = 4,
                extentRows = 4,
                stones = listOf(Stone(4, 3, StoneColor.Black), Stone(5, 4, StoneColor.White)),
            )
        val stones = convertStonesFromBoardToCanvas(kifu, kifu.stones)
        assertEquals(listOf(Stone(1, 2, StoneColor.Black), Stone(2, 1, StoneColor.White)), stones)
    }

    @Test
    fun 表示範囲外の石は除外される() {
        val kifu =
            KifuData(
                "",
                boardSize = BoardSize.Nineteen,
                offsetCol = 0,
                offsetRow = 0,
                extentCols = 5,
                extentRows = 5,
                stones =
                    listOf(
                        Stone(5, 0, StoneColor.Black),
                        Stone(1, 5, StoneColor.White),
                        Stone(2, 2, StoneColor.White),
                    ),
            )
        val stones = convertStonesFromBoardToCanvas(kifu, kifu.stones)
        assertEquals(listOf(Stone(2, 2, StoneColor.White)), stones)
    }

    @Test
    fun 表示範囲の端にある石は含まれる() {
        val kifu =
            KifuData(
                "",
                boardSize = BoardSize.Nineteen,
                offsetCol = 1,
                offsetRow = 3,
                extentCols = 5,
                extentRows = 4,
                stones =
                    listOf(
                        Stone(1, 3, StoneColor.White),
                        Stone(1, 6, StoneColor.Black),
                        Stone(5, 3, StoneColor.White),
                        Stone(5, 6, StoneColor.Black),
                        Stone(0, 0, StoneColor.White),
                    ),
            )
        val stones = convertStonesFromBoardToCanvas(kifu, kifu.stones)
        assertEquals(
            listOf(
                Stone(0, 3, StoneColor.White),
                Stone(0, 0, StoneColor.Black),
                Stone(4, 3, StoneColor.White),
                Stone(4, 0, StoneColor.Black),
            ),
            stones,
        )
    }
}
