package com.suvodeep.supergrocer.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundedProgressIndicator(progress: Float) {
    CircularProgressIndicator(
        progress = { progress },
        modifier = Modifier.size(80.dp),
        color = Color.Blue,
        strokeWidth = 8.dp,
        trackColor = Color.LightGray,
        strokeCap = StrokeCap.Round, // This rounds the ends
    )
}
