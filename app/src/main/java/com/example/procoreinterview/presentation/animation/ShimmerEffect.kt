package com.example.procoreinterview.presentation.animation


import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.tween
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color

@Composable

fun ShimmerEffect() {
    // Animatable to handle the shimmer translation movement
    val shimmerTranslate = remember { Animatable(0f) }

    // Infinite animation for the shimmer effect
    LaunchedEffect(Unit) {
        shimmerTranslate.animateTo(
            targetValue = 1000f,  // Travel further to increase shimmer visibility
            animationSpec = infiniteRepeatable(
                animation = tween(durationMillis = 1500, easing = LinearEasing)  // Slower and more noticeable
            )
        )
    }

    // Box with shimmer effect
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                // Create the shimmer gradient with more contrasting colors
                brush = Brush.linearGradient(
                    colors = listOf(
                        Color.LightGray.copy(alpha = 0.6f),  // Start with transparent light gray
                        Color.Gray.copy(alpha = 0.4f),       // Darker gray for contrast
                        Color.LightGray.copy(alpha = 0.6f)   // Back to light gray
                    ),
                    start = Offset.Zero,  // Start position of the gradient
                    end = Offset(x = shimmerTranslate.value, y = shimmerTranslate.value)  // Dynamic end position
                )
            )
    )
}