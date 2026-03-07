package com.skdassoc.tsumegolet.scene

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skdassoc.tsumegolet.KifuData

@Composable
fun ListScene(kifuList: List<KifuData>) {
    LazyColumn(modifier = Modifier.fillMaxSize()) {
        items(kifuList) { kifu ->
            Text(
                text = kifu.title,
                fontSize = 18.sp,
                modifier = Modifier.fillMaxWidth().padding(horizontal = 16.dp, vertical = 12.dp),
            )
            HorizontalDivider()
        }
    }
}
