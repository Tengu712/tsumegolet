package com.skdassoc.tsumegolet.scene

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skdassoc.tsumegolet.component.GoBoard
import com.skdassoc.tsumegolet.component.LabelButton
import com.skdassoc.tsumegolet.component.SceneHeader
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.model.putOrRemoveStone

private enum class Status {
    Solving,
    Incorrected,
    Answered,
    Editing,
}

@Composable
fun QuestionScene(
    kifu: KifuData,
    onEdit: (() -> Unit)? = null,
    onDelete: (() -> Unit)? = null,
    onNext: (() -> Unit)? = null,
    onFinish: (() -> Unit)? = null,
) {
    var status by remember { mutableStateOf(Status.Solving) }
    var stones by remember { mutableStateOf(kifu.stones) }
    var turn by remember { mutableStateOf(kifu.answerTurn) }
    var showDeleteDialog by remember { mutableStateOf(false) }

    Column(modifier = Modifier.fillMaxSize()) {
        SceneHeader {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                if (onDelete != null) LabelButton("削除") { showDeleteDialog = true }
                if (onEdit != null) LabelButton("編集") { onEdit() }
                if (onFinish != null) LabelButton("終了") { onFinish() }
                if (onNext != null) LabelButton("次へ") { onNext() }
            }
        }
        BoxWithConstraints(modifier = Modifier.weight(1f), contentAlignment = Alignment.Center) {
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
                                val (newStones, newTurn) = putOrRemoveStone(stones, turn, coord)
                                stones = newStones
                                turn = newTurn
                                if (status == Status.Answered) status = Status.Editing
                            }
                            else -> {
                                if (coord == kifu.answer) {
                                    val (newStones, newTurn) = putOrRemoveStone(stones, turn, coord)
                                    stones = newStones
                                    turn = newTurn
                                    status = Status.Answered
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
                when (status) {
                    Status.Answered,
                    Status.Editing -> Text(kifu.description, fontSize = 16.sp)
                    // NOTE: レイアウト維持のため。
                    else -> Text("")
                }
            }
        }
    }

    if (showDeleteDialog) {
        AlertDialog(
            onDismissRequest = { showDeleteDialog = false },
            text = { Text("「${kifu.title}」を削除しますか？") },
            confirmButton = { TextButton(onClick = { onDelete?.invoke() }) { Text("削除") } },
            dismissButton = { TextButton(onClick = { showDeleteDialog = false }) { Text("キャンセル") } },
        )
    }
}
