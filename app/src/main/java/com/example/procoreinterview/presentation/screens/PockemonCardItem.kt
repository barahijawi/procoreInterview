package com.example.procoreinterview.presentation.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.example.procoreinterview.domain.PockemonCard
import com.example.procoreinterview.util.LoadingImage
import com.example.procoreinterview.util.ParallaxEffect

@Composable
fun PockemonCardItem(card: PockemonCard,onClick: () -> Unit) {


        Column (
            verticalArrangement = Arrangement.SpaceBetween
        ){
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(130.dp)
                    .padding(8.dp)
                    .clickable(onClick = onClick)
            ) {
                LoadingImage(pokemonImageUrl = card.imageUrlSmall)
//                Image(
//                    painter = rememberAsyncImagePainter(card.imageUrlSmall),
//                    contentDescription = null,
//                    contentScale = ContentScale.Fit,
//                    modifier = Modifier.fillMaxSize()
//
//                )
//                ParallaxEffect(imageUrl = card.imageUrlSmall)
            }
//            Text(text = card.name, style = MaterialTheme.typography.caption, color = Color.White,
//                 modifier = Modifier
//                     .align(alignment = Alignment.CenterHorizontally)
//            )
//            Text(text = "HP: ${card.hp}",style = MaterialTheme.typography.caption, color = Color.White,
//                 modifier = Modifier
//                     .align(alignment = Alignment.CenterHorizontally))
        }


}
