package com.skdassoc.tsumegolet.scene

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.sp
import com.skdassoc.tsumegolet.component.GoBoard
import com.skdassoc.tsumegolet.model.KifuData

@Composable
fun QuestionScene(kifu: KifuData) {
    BoxWithConstraints( contentAlignment = Alignment.Center) {
        val maxW = maxWidth
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
        ) {
            Text(kifu.title, fontSize = 20.sp)
            GoBoard(kifu = kifu, maxWidth = maxW)
            Text(kifu.description, fontSize = 16.sp)
        }
    }
}
