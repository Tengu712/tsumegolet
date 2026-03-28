package com.skdassoc.tsumegolet.model

import org.junit.Assert.assertEquals
import org.junit.Test

class KifuTransformTest {
    private val base =
        KifuData(
            "",
            boardSize = BoardSize.Nine,
            offsetCol = 2,
            offsetRow = 1,
            extentCols = 4,
            extentRows = 3,
            stones = listOf(Stone(BoardCoord(3, 2), StoneColor.Black)),
            answer = BoardCoord(4, 1),
            answerTurn = StoneColor.Black,
        )

    @Test
    fun 盤面を反時計回りに90度回転する() {
        val result = transformKifu(base, 1, false)
        assertEquals(5, result.offsetCol)
        assertEquals(2, result.offsetRow)
        assertEquals(3, result.extentCols)
        assertEquals(4, result.extentRows)
        assertEquals(BoardCoord(6, 3), result.stones[0].coord)
        assertEquals(BoardCoord(7, 4), result.answer)
    }

    @Test
    fun 盤面を反時計回りに180度回転する() {
        val result = transformKifu(base, 2, false)
        assertEquals(3, result.offsetCol)
        assertEquals(5, result.offsetRow)
        assertEquals(4, result.extentCols)
        assertEquals(3, result.extentRows)
        assertEquals(BoardCoord(5, 6), result.stones[0].coord)
        assertEquals(BoardCoord(4, 7), result.answer)
    }

    @Test
    fun 盤面を反時計回りに270度回転する() {
        val result = transformKifu(base, 3, false)
        assertEquals(1, result.offsetCol)
        assertEquals(3, result.offsetRow)
        assertEquals(3, result.extentCols)
        assertEquals(4, result.extentRows)
        assertEquals(BoardCoord(2, 5), result.stones[0].coord)
        assertEquals(BoardCoord(1, 4), result.answer)
    }

    @Test
    fun 手番を入れ替える() {
        val result = transformKifu(base, 0, true)
        assertEquals(StoneColor.White, result.stones[0].color)
        assertEquals(StoneColor.White, result.answerTurn)
        assertEquals(base.stones[0].coord, result.stones[0].coord)
        assertEquals(base.answer, result.answer)
    }
}
