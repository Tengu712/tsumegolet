package com.skdassoc.tsumegolet.scene

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.skdassoc.tsumegolet.component.LabelSwitch

@Composable
fun QuizSettingScene(onDecided: (Boolean, Boolean) -> Unit) {
    var checkedRotation by remember { mutableStateOf(true) }
    var checkedSwapping by remember { mutableStateOf(true) }

    Box(
        modifier = Modifier.fillMaxSize().padding(horizontal = 16.dp, vertical = 8.dp),
        contentAlignment = Alignment.Center,
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            LabelSwitch(
                "盤面回転",
                checked = checkedRotation,
                onCheckedChange = { checkedRotation = it },
            )
            LabelSwitch(
                "手番入替",
                checked = checkedSwapping,
                onCheckedChange = { checkedSwapping = it },
            )
            Button(onClick = { onDecided(checkedRotation, checkedSwapping) }) { Text("決定") }
        }
    }
}
