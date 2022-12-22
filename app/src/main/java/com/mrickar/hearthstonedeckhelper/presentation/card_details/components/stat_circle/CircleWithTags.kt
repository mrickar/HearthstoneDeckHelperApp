package com.mrickar.hearthstonedeckhelper.presentation.card_details.components.stat_circle

import android.graphics.Paint
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.*
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun CircleWithTags(
    rarityColor: Color,
    diameter:Int,
    sweepAngle:Float
)
{
    val tagList:List<String> = listOf("rarity","atk","mana","health")
    for (ind in tagList.indices) {
        Canvas(
            modifier = Modifier
                .width(diameter.dp)
                .height(diameter.dp),
        )
        {
            val size= Size(diameter.dp.toPx(), diameter.dp.toPx())
            val startAngle=((360 - sweepAngle * ind)-1.5* sweepAngle).toFloat()
            val ovalRect= Rect(Offset.Zero, size = size)

            val circlePath: Path = Path().apply {
                addArc(oval=ovalRect, startAngleDegrees = startAngle, sweepAngleDegrees = sweepAngle)
            }
            drawPath(
                path = circlePath,
                brush= Brush.verticalGradient(
                    colors= listOf(
                        rarityColor,
                        Color.LightGray
                    )
                ),
                style = Stroke(
                    width = 4.dp.toPx()
                )
            )
            val linesPath: Path = Path().apply {
                arcTo(
                    ovalRect, (startAngle+0.5* sweepAngle).toFloat(), sweepAngle,false
                )
                lineTo(ovalRect.center.x,ovalRect.center.y)
            }
            drawPath(
                path = linesPath,
                color= rarityColor,
                style = Stroke(
                    width = 1.dp.toPx()
                )
            )
            drawIntoCanvas {
                it.nativeCanvas.drawTextOnPath(
                    tagList[ind],
                    circlePath.asAndroidPath(),
                    -0f,
                    (-diameter/40).dp.toPx(),
                    androidx.compose.ui.graphics.Paint().asFrameworkPaint().apply {
                        textSize = 20.sp.toPx()
                        textAlign = Paint.Align.CENTER
                    }
                )
            }
        }
    }
}