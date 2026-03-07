package com.skdassoc.tsumegolet.scene

import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.skdassoc.tsumegolet.component.GoBoard
import com.skdassoc.tsumegolet.model.KifuData

@Composable
fun QuestionScene(kifu: KifuData) {
    BoxWithConstraints(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        GoBoard(kifu = kifu, maxWidth = maxWidth)
    }
}
