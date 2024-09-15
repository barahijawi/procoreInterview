package com.example.procoreinterview.util

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.SubcomposeAsyncImage
import coil.compose.rememberAsyncImagePainter

@Composable
fun LoadingImage(pokemonImageUrl: String) {
    val painter = rememberAsyncImagePainter(pokemonImageUrl)
    val painterState = painter.state

    // Use SubcomposeAsyncImage to handle loading and display
    SubcomposeAsyncImage(
        model = pokemonImageUrl,
        contentDescription = null,
        contentScale = ContentScale.Crop,
//        modifier = Modifier.size(256.dp),
        loading = {
            // Show a circular progress indicator while loading
           ShimmerEffect()
        },
        error = {
            // Show a placeholder in case of an error
            Text(text = "Failed to load image")
        },
        success = { state ->
            // Show the actual image once it's loaded
            Image(painter = painter, contentDescription = null,
                  contentScale = ContentScale.Fit,
                  modifier = Modifier.fillMaxSize())
        }
    )
}