package com.skdassoc.tsumegolet.component

import androidx.compose.foundation.clickable
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration

@Composable
fun LabelButton(label: String, modifier: Modifier = Modifier, onClick: () -> Unit) =
    Text(
        label,
        color = Color.Black,
        style = TextStyle(textDecoration = TextDecoration.Underline),
        modifier = modifier.clickable(onClick = onClick),
    )
