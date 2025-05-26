package com.example.stock.presentation.composables

import android.graphics.Paint
import android.graphics.Point
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.stock.data.models.timeSeries.WeeklyData
import com.example.stock.presentation.ui.getCurrentMonthPoints
import com.example.stock.presentation.ui.getCurrentWeekPoints
import com.example.stock.presentation.ui.getCurrentYearPoints
import com.example.stock.presentation.ui.toDate
import kotlin.math.round
import kotlin.math.roundToInt

@Composable
fun StockChart(
    info: Map<String, WeeklyData> = emptyMap(),
    modifier: Modifier = Modifier,
    graphColor: Color = MaterialTheme.colorScheme.primary,
    type: Int = 0,
) {
    val all = info.toList().sortedBy { it.first }
    val infos = when(type){
        1->all.getCurrentWeekPoints()
        2->all.getCurrentMonthPoints()
        3->all.getCurrentYearPoints()
        else -> {all}
    }
    val textColor = MaterialTheme.colorScheme.outline
    val spacing = 50f
    val upperValue = remember(infos) {
        (infos.maxOfOrNull { it.second.close.toFloat() }?.plus(1))?.roundToInt() ?: 0
    }
    val lowerValue = remember(infos) {
        (infos.minOfOrNull { it.second.close.toFloat() })?.toInt() ?: 0
    }
    val density = LocalDensity.current
    val textPaint = remember(density) {
        Paint().apply {
            color = textColor.toArgb()
            textAlign = Paint.Align.CENTER
            textSize = density.run { 12.sp.toPx() }
        }
    }
    Canvas(modifier = modifier.padding(16.dp).fillMaxWidth()) {
        val spacePerHour = (size.width-spacing) / (infos.size)
        val priceStep = (upperValue - lowerValue) / 5f
        (0..4).forEach { i ->
            drawContext.canvas.nativeCanvas.apply {
                drawText(
                    round(lowerValue + priceStep * i).toString(),
                    5f,
                    size.height - spacing - i * size.height / 5f,
                    textPaint
                )
            }
        }
        var lastX = 0f
        val strokePath = Path().apply {
            val height = size.height
            for (i in infos.indices) {
                val info = infos[i]
                val nextInfo = infos.getOrNull(i + 1) ?: infos.last()
                val leftRatio =
                    (info.second.close.toFloat() - lowerValue) / (upperValue - lowerValue)
                val rightRatio =
                    (nextInfo.second.close.toFloat() - lowerValue) / (upperValue - lowerValue)
                val x1 = spacing + i * spacePerHour
                val y1 = height - spacing - (leftRatio * height).toFloat()
                val x2 = spacing + (i + 1) * spacePerHour
                val y2 = height - spacing - (rightRatio * height).toFloat()
                if (i == 0) {
                    moveTo(x1, y1)
                }
                lastX = (x1 + x2) / 2f
                quadraticBezierTo(x1, y1, lastX, (y1 + y2) / 2f)
            }
        }
        drawPath(
            path = strokePath,
            color = graphColor,
            style = Stroke(
                width = 3.dp.toPx(),
                cap = StrokeCap.Round
            )
        )
    }
}