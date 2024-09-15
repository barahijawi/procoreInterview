package com.example.procoreinterview.presentation.screens

import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.LinearOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.procoreinterview.util.FadeAndSlideInText
import com.example.procoreinterview.util.ParallaxEffect

@Composable
fun PockemonDetailsScreen(
    pokemonId: String,
    pokemonName: String,
    pokemonHp: String,
    pokemonImageUrl: String,
    pokemonSuperType: String,
    pokemonTypes: String,
    onBackPressed: () -> Unit
)
{

    val rotationOnY = remember { Animatable(0f) }
    LaunchedEffect(Unit) {
        // Perform multiple rotations
        repeat(1) {
            // Rotate from 0 to 180 degrees (half flip)
            rotationOnY.animateTo(
                targetValue = 360f,
                animationSpec = tween(durationMillis = 1500, easing = LinearOutSlowInEasing)
            )
            // Reset to 0 to make it flip continuously
            rotationOnY.snapTo(0f)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = pokemonName) },
                      navigationIcon = {
                          IconButton(onClick = { onBackPressed() }) {
                              Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Back")
                          }
                      }
            )
        },
        content = { paddingValues ->
            Column(modifier = Modifier.padding(paddingValues)) {
                // Pokémon image
                Box(modifier = Modifier
                    .padding(top = 16.dp)
//                    .size(200.dp)
                    .graphicsLayer {
                        rotationY = rotationOnY.value
                        cameraDistance = 12f * density
                    }) {
                    Image(
                        painter = rememberAsyncImagePainter(pokemonImageUrl),
                        contentDescription = pokemonName,
                        modifier = Modifier

                            .fillMaxWidth()
                    )
                }
//                ParallaxEffect(imageUrl = pokemonImageUrl)

                Spacer(modifier = Modifier.height(16.dp))

                // Pokémon details
                FadeAndSlideInText(text = "Name: $pokemonName",)
                FadeAndSlideInText(text = "HP: $pokemonHp",)
                FadeAndSlideInText(text = "SuperType: $pokemonSuperType")
                FadeAndSlideInText(text = "Types: $pokemonTypes")
            }
        }
    )
}
