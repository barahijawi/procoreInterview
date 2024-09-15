package com.example.procoreinterview.util

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.IntOffset

@Composable
fun FadeAndSlideInText(text: String) {
    val alpha = remember { Animatable(0f) }
    val offsetX = remember { Animatable(-500f) }

    LaunchedEffect(Unit) {
        alpha.animateTo(
            targetValue = 1f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
        offsetX.animateTo(
            targetValue = 0f,
            animationSpec = tween(durationMillis = 1000, easing = LinearEasing)
        )
    }

    Box (
        modifier = Modifier.fillMaxWidth()
        , contentAlignment = Alignment.Center
    ){
        Text(
            text = text,
            modifier = Modifier
                .alpha(alpha.value)
                .offset { IntOffset(offsetX.value.toInt(), 0) },
            style = MaterialTheme.typography.body1,
            textAlign = TextAlign.Center

        )
    }
}