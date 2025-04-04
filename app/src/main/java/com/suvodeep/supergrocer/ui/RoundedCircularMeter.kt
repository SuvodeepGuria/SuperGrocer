package com.suvodeep.supergrocer.ui

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun RoundedCircularMeter(progress: Float) {
    Canvas(modifier = Modifier.size(100.dp)) {
        val strokeWidth = 12f
        val radius = size.minDimension / 2 - strokeWidth

        drawArc(
            color = Color.LightGray,
            startAngle = 135f,
            sweepAngle = 270f,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(strokeWidth, strokeWidth)
        )

        drawArc(
            color = Color.Red,
            startAngle = 135f,
            sweepAngle = 270f * progress,
            useCenter = false,
            style = Stroke(width = strokeWidth, cap = StrokeCap.Round),
            size = Size(radius * 2, radius * 2),
            topLeft = Offset(strokeWidth, strokeWidth)
        )
    }
}
