package com.skdassoc.tsumegolet.model

import org.junit.Assert.assertEquals
import org.junit.Test

class BoardLayoutTest {
    // 9路盤
    @Test
    fun fullBoard() {
        val layout = computeBoardLayout(KifuData("", boardSize = BoardSize.Nine))
        assertEquals(9, layout.cols)
        assertEquals(9, layout.rows)
        assertEquals(0.5f, layout.left)
        assertEquals(8.5f, layout.right)
        assertEquals(0.5f, layout.top)
        assertEquals(8.5f, layout.bottom)
        assertEquals(emptyList<Pair<Int, Int>>(), layout.stars)
    }

    // 13路盤の左下7x7
    @Test
    fun bottomLeftClip() {
        val layout = computeBoardLayout(KifuData("", boardSize = BoardSize.Thirteen, offsetCol = 0, offsetRow = 0, extentCols = 7, extentRows = 7))
        assertEquals(7, layout.cols)
        assertEquals(7, layout.rows)
        assertEquals(0.5f, layout.left)
        assertEquals(7.0f, layout.right)
        assertEquals(0.0f, layout.top)
        assertEquals(6.5f, layout.bottom)
        assertEquals(listOf(Pair(3, 3), Pair(6, 0)), layout.stars)
    }

    // 19路盤の左下7x7
    @Test
    fun bottomLeftClip2() {
        val layout = computeBoardLayout(KifuData("", boardSize = BoardSize.Nineteen, offsetCol = 0, offsetRow = 0, extentCols = 7, extentRows = 7))
        assertEquals(7, layout.cols)
        assertEquals(7, layout.rows)
        assertEquals(0.5f, layout.left)
        assertEquals(7.0f, layout.right)
        assertEquals(0.0f, layout.top)
        assertEquals(6.5f, layout.bottom)
        assertEquals(listOf(Pair(3, 3)), layout.stars)
    }

    // 19路盤の右上7x5
    @Test
    fun topRightClip() {
        val layout = computeBoardLayout(KifuData("", boardSize = BoardSize.Nineteen, offsetCol = 12, offsetRow = 14, extentCols = 7, extentRows = 5))
        assertEquals(7, layout.cols)
        assertEquals(5, layout.rows)
        assertEquals(0.0f, layout.left)
        assertEquals(6.5f, layout.right)
        assertEquals(0.5f, layout.top)
        assertEquals(5.0f, layout.bottom)
        assertEquals(listOf(Pair(3, 3)), layout.stars)
    }

    // 19路盤の中央9x8
    @Test
    fun centerClip() {
        val layout = computeBoardLayout(KifuData("", boardSize = BoardSize.Nineteen, offsetCol = 5, offsetRow = 7, extentCols = 9, extentRows = 8))
        assertEquals(9, layout.cols)
        assertEquals(8, layout.rows)
        assertEquals(0.0f, layout.left)
        assertEquals(9.0f, layout.right)
        assertEquals(0.0f, layout.top)
        assertEquals(8.0f, layout.bottom)
        assertEquals(listOf(Pair(4, 5)), layout.stars)
    }
}
