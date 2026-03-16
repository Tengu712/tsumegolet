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
import com.skdassoc.tsumegolet.model.Stone
import com.skdassoc.tsumegolet.model.flipped

private enum class Status {
    Solving,
    Incorrected,
    Answered,
    Editing,
}

@Composable
fun QuestionScene(kifu: KifuData) {
    var status by remember { mutableStateOf(Status.Solving) }
    var stones by remember { mutableStateOf(kifu.stones) }
    var turn by remember { mutableStateOf(kifu.answerTurn) }
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
                turn = turn,
                maxWidth = maxW,
                maxHeight = maxH / 2,
                onTap = { coord ->
                    when (status) {
                        Status.Answered,
                        Status.Editing -> {
                            val index = stones.indexOfFirst { it.coord == coord }
                            if (index >= 0) {
                                stones = stones.toMutableList().also { it.removeAt(index) }
                            } else {
                                stones = stones.toMutableList().also { it.add(Stone(coord, turn)) }
                                turn = turn.flipped
                            }
                            if (status == Status.Answered) status = Status.Editing
                        }
                        else -> {
                            if (coord == kifu.answer) {
                                status = Status.Answered
                                stones = stones.toMutableList().also { it.add(Stone(coord, turn)) }
                                turn = turn.flipped
                            } else {
                                status = Status.Incorrected
                            }
                        }
                    }
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
