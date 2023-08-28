package com.olgag.currencyconverter.screens

import androidx.compose.animation.core.*
import androidx.compose.foundation.Canvas
import com.olgag.currencyconverter.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.foundation.Image
import androidx.compose.ui.unit.dp
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.graphics.drawscope.drawIntoCanvas
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import android.graphics.Color
import androidx.compose.ui.graphics.nativeCanvas
import androidx.core.graphics.withSave


@Composable
fun LoaderShimmerEffect() {
    val transition = rememberInfiniteTransition()
    val animatedValue by transition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            tween(durationMillis = 2000, easing = LinearEasing),
            RepeatMode.Restart
        )
    )
    ShimmerItem(modifier = Modifier.fillMaxWidth(), animatedValue = animatedValue)
    ImageAnimated()
}

@Composable
fun ImageAnimated() {
    val value by rememberInfiniteTransition().animateFloat(
        initialValue = 0.1f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(
                durationMillis = 1000,
                easing = LinearEasing
            ),
            repeatMode = RepeatMode.Reverse
        )
    )

    Image(
        painter = painterResource(id = R.drawable.ic_launcher),
        contentDescription = "",
        alignment = Alignment.Center, modifier = Modifier
            .fillMaxSize()
            .graphicsLayer {
                scaleX = value
                scaleY = value
            }
            .size(25.dp)
    )
}

@Composable
fun ShimmerItem(modifier: Modifier, animatedValue: Float) {
    Box(modifier) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            drawIntoCanvas { canvas ->
                val paint = androidx.compose.ui.graphics.Paint().asFrameworkPaint()
                val width = size.width
                val height = size.height
                val colors = intArrayOf(
                    Color.TRANSPARENT,
                    Color.rgb(160, 123, 11),
                    Color.TRANSPARENT
                )
                val positions = floatArrayOf(
                    0f,
                    animatedValue,
                    1f
                )
                val shader = android.graphics.LinearGradient(
                    0f, 0f, width, height,
                    colors, positions,
                    android.graphics.Shader.TileMode.CLAMP
                )
                paint.shader = shader

                canvas.nativeCanvas.withSave {
                    val rect = android.graphics.RectF(0f, 0f, width, height)
                    drawRoundRect(rect, 8f, 8f, paint)
                }
            }
        }
    }
}
