package com.example.procoreinterview.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.procoreinterview.presentation.viewmodel.PockemonViewModel

@Composable
fun PockemonListScreen(viewModel: PockemonViewModel = hiltViewModel()) {
    val pockemonCards by viewModel.pockemonCards.collectAsState(initial = emptyList())

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Pockemon Screen") })
        },
        content = { paddingValues ->
            LazyVerticalGrid(
                columns = GridCells.Fixed(4),
                modifier = Modifier.padding(paddingValues),
                contentPadding = PaddingValues(8.dp)
            ) {
                items(pockemonCards) { card ->
                    PockemonCardItem(card = card)
                }
            }
        }
    )
}
