package com.skdassoc.tsumegolet.scene

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.skdassoc.tsumegolet.component.LabelButton
import com.skdassoc.tsumegolet.component.SceneHeader
import com.skdassoc.tsumegolet.model.KifuData

@Composable
fun ListScene(kifuList: List<KifuData>, onSelect: (Int) -> Unit, onAdd: () -> Unit) {
    Column(modifier = Modifier.fillMaxSize()) {
        SceneHeader { LabelButton("追加") { onAdd() } }
        LazyColumn(modifier = Modifier.weight(1f)) {
            itemsIndexed(kifuList) { index, kifu ->
                Text(
                    text = kifu.title,
                    fontSize = 18.sp,
                    modifier =
                        Modifier.fillMaxWidth()
                            .clickable { onSelect(index) }
                            .padding(horizontal = 16.dp, vertical = 12.dp),
                )
                HorizontalDivider()
            }
        }
    }
}
