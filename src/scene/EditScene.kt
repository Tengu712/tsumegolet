package com.skdassoc.tsumegolet.scene

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.skdassoc.tsumegolet.component.GoBoard
import com.skdassoc.tsumegolet.model.BoardCoord
import com.skdassoc.tsumegolet.model.BoardSize
import com.skdassoc.tsumegolet.model.KifuData
import com.skdassoc.tsumegolet.model.StoneColor
import com.skdassoc.tsumegolet.model.displayName
import com.skdassoc.tsumegolet.model.putOrRemoveStone

@Composable
fun EditScene(kifu: KifuData, onSave: (KifuData) -> Unit) {
    var title by remember { mutableStateOf(kifu.title) }
    var description by remember { mutableStateOf(kifu.description) }
    var boardSize by remember { mutableStateOf(kifu.boardSize) }
    var extentColsStr by remember { mutableStateOf(kifu.extentCols.toString()) }
    var extentRowsStr by remember { mutableStateOf(kifu.extentRows.toString()) }
    var offsetColStr by remember { mutableStateOf(kifu.offsetCol.toString()) }
    var offsetRowStr by remember { mutableStateOf(kifu.offsetRow.toString()) }
    var answerColStr by remember { mutableStateOf(kifu.answer.col.toString()) }
    var answerRowStr by remember { mutableStateOf(kifu.answer.row.toString()) }
    var answerTurn by remember { mutableStateOf(kifu.answerTurn) }
    var stones by remember { mutableStateOf(kifu.stones) }
    var editTurn by remember { mutableStateOf(StoneColor.Black) }

    val extentCols = extentColsStr.toIntOrNull()
    val extentRows = extentRowsStr.toIntOrNull()
    val offsetCol = offsetColStr.toIntOrNull()
    val offsetRow = offsetRowStr.toIntOrNull()
    val answerCol = answerColStr.toIntOrNull()
    val answerRow = answerRowStr.toIntOrNull()

    val extentColsValid = extentCols != null && extentCols in 1..boardSize.value
    val extentRowsValid = extentRows != null && extentRows in 1..boardSize.value
    val offsetColValid =
        offsetCol != null &&
            extentCols != null &&
            offsetCol >= 0 &&
            offsetCol + extentCols <= boardSize.value
    val offsetRowValid =
        offsetRow != null &&
            extentRows != null &&
            offsetRow >= 0 &&
            offsetRow + extentRows <= boardSize.value
    val answerColValid =
        answerCol != null &&
            offsetCol != null &&
            extentCols != null &&
            answerCol in offsetCol until offsetCol + extentCols
    val answerRowValid =
        answerRow != null &&
            offsetRow != null &&
            extentRows != null &&
            answerRow in offsetRow until offsetRow + extentRows
    val allValid =
        extentColsValid &&
            extentRowsValid &&
            offsetColValid &&
            offsetRowValid &&
            answerColValid &&
            answerRowValid

    val currentKifu =
        if (allValid)
            KifuData(
                title = title,
                description = description,
                boardSize = boardSize,
                extentCols = extentCols!!,
                extentRows = extentRows!!,
                offsetCol = offsetCol!!,
                offsetRow = offsetRow!!,
                stones = stones,
                answer = BoardCoord(answerCol!!, answerRow!!),
                answerTurn = answerTurn,
            )
        else null

    Box(modifier = Modifier.fillMaxSize()) {
        Column(
            modifier =
                Modifier.fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(start = 16.dp, end = 16.dp, top = 48.dp, bottom = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            OutlinedTextField(
                value = title,
                onValueChange = { title = it },
                label = { Text("タイトル") },
                singleLine = true,
                modifier = Modifier.fillMaxWidth(),
            )

            OutlinedTextField(
                value = description,
                onValueChange = { description = it },
                label = { Text("説明") },
                minLines = 1,
                modifier = Modifier.fillMaxWidth(),
            )

            Text("碁盤サイズ")
            Row(verticalAlignment = Alignment.CenterVertically) {
                BoardSize.entries.forEach { size ->
                    RadioButton(selected = boardSize == size, onClick = { boardSize = size })
                    Text("${size.value}路")
                }
            }

            Text("表示サイズ")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = extentColsStr,
                    onValueChange = { extentColsStr = it },
                    label = { Text("行") },
                    isError = !extentColsValid,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                )
                OutlinedTextField(
                    value = extentRowsStr,
                    onValueChange = { extentRowsStr = it },
                    label = { Text("列") },
                    isError = !extentRowsValid,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                )
            }

            Text("表示オフセット(左下原点)")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = offsetColStr,
                    onValueChange = { offsetColStr = it },
                    label = { Text("行") },
                    isError = !offsetColValid,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                )
                OutlinedTextField(
                    value = offsetRowStr,
                    onValueChange = { offsetRowStr = it },
                    label = { Text("列") },
                    isError = !offsetRowValid,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                )
            }

            Text("正解手番")
            Row(verticalAlignment = Alignment.CenterVertically) {
                StoneColor.entries.forEach { color ->
                    RadioButton(selected = answerTurn == color, onClick = { answerTurn = color })
                    Text("${color.displayName}先")
                }
            }

            Text("正解座標(左下原点)")
            Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                OutlinedTextField(
                    value = answerRowStr,
                    onValueChange = { answerRowStr = it },
                    label = { Text("行") },
                    isError = !answerRowValid,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                )
                OutlinedTextField(
                    value = answerColStr,
                    onValueChange = { answerColStr = it },
                    label = { Text("列") },
                    isError = !answerColValid,
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                    modifier = Modifier.weight(1f),
                )
            }

            if (currentKifu != null) {
                BoxWithConstraints {
                    GoBoard(
                        kifu = currentKifu,
                        stones = stones,
                        turn = editTurn,
                        maxWidth = maxWidth,
                        maxHeight = maxWidth,
                        onTap = { coord ->
                            val (newStones, newTurn) = putOrRemoveStone(stones, editTurn, coord)
                            stones = newStones
                            editTurn = newTurn
                        },
                    )
                }
            }
        }

        if (currentKifu != null) {
            Text(
                "保存",
                color = Color.Black,
                style = TextStyle(textDecoration = TextDecoration.Underline),
                modifier =
                    Modifier.align(Alignment.TopEnd).padding(12.dp).clickable {
                        onSave(currentKifu)
                    },
            )
        }
    }
}
