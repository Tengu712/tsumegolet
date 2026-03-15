package com.skdassoc.tsumegolet.scene

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.skdassoc.tsumegolet.component.GoBoard
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.model.convertCoordFromBoardToCanvas

private enum class Status {
    Solving,
    Incorrected,
    Answered,
    Editing,
}

@Composable
fun QuestionScene(kifu: KifuData) {
    val (answerCanvasCol, answerCanvasRow) =
        convertCoordFromBoardToCanvas(kifu, kifu.answerCol, kifu.answerRow)

    var stones by remember { mutableStateOf(kifu.stones) }
    var status by remember { mutableStateOf(Status.Solving) }
    val editable = remember(status) { status == Status.Answered || status == Status.Editing }

    BoxWithConstraints(contentAlignment = Alignment.Center) {
        val maxW = maxWidth
        val maxH = maxHeight
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(kifu.title, fontSize = 20.sp)
            GoBoard(
                kifu = kifu,
                stones = stones,
                turn = kifu.answerTurn,
                maxWidth = maxW,
                maxHeight = maxH / 2,
                onTap = { col, row ->
                    if (col == answerCanvasCol && row == answerCanvasRow) status = Status.Answered
                },
            )
            when (status) {
                Status.Incorrected -> Text("不正解")
                Status.Answered -> Text("正解")
                // NOTE: レイアウト維持のため。
                else -> Text("")
            }
            Text(kifu.description, fontSize = 16.sp)
        }
    }
}
