package com.example.procoreinterview.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FloatingActionButton
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.RangeSlider
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FilterList
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.procoreinterview.data.database.MinMaxHp
import com.example.procoreinterview.domain.PockemonCard
import com.example.procoreinterview.presentation.viewmodel.PockemonViewModel
import com.example.procoreinterview.util.NetworkUtil.isNetworkAvailable
import com.example.procoreinterview.util.PockemonSortingOptions
import com.example.procoreinterview.util.ShimmerEffect
import com.example.procoreinterview.util.SortOption
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun PockemonListScreen(onCardClick: (PockemonCard) -> Unit, viewModel: PockemonViewModel = hiltViewModel())
{
    val pockemonCards by viewModel.pockemonCards.collectAsState(initial = emptyList())
    val errorMessage by viewModel.errorState.collectAsState()
    // Check for network availability
    val isNetworkAvailable = isNetworkAvailable(LocalContext.current)
    val minMaxHp by viewModel.minMaxHp.collectAsState()
    var selectedSortOption by remember { mutableStateOf(SortOption.NONE) }

    val sliderPosition = remember(minMaxHp) {
        // Use a valid range only if both min and max values are non-zero and valid
        if (minMaxHp.minHp > 0 && minMaxHp.maxHp > minMaxHp.minHp) {
            mutableStateOf(minMaxHp.minHp.toFloat()..minMaxHp.maxHp.toFloat())
        } else {
            null // No valid range yet
        }
    }
    val loading by viewModel.loading.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.loadPockemonCards(isNetworkAvailable)
        viewModel.loadMinMaxHp()

    }

    sliderPosition?.let { validSliderPosition ->
        LaunchedEffect(validSliderPosition.value) {
            delay(1000L) // Debounce the filtering by 500ms
            viewModel.applyHpFilter(
                minHp = validSliderPosition.value.start.toInt(),
                maxHp = validSliderPosition.value.endInclusive.toInt()
            )
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Pockemon Screen") })
        },

        backgroundColor = Color.Black,
        content = { paddingValues ->

            Column {
                PockemonSortingOptions(
                    onSortChange = { sortOption ->
                        selectedSortOption = sortOption
                        viewModel.sortPokemonCards(sortOption) // Trigger sorting in ViewModel
                    }
                )
                if (loading)
                {
                    ShimmerEffect()
                } else
                {

                    // Show error message if any
                    if (errorMessage != null )
                    {
                        Text(text = errorMessage!!, color = MaterialTheme.colors.error)
                    }

                        sliderPosition?.let { validSliderPosition ->
                            Text(
                                text = "HP Range: ${validSliderPosition.value.start.toInt()} - ${validSliderPosition.value.endInclusive.toInt()}",
                                color = Color.White,
                                modifier = Modifier.align(Alignment.CenterHorizontally)

                            )
                            RangeSlider(
                                value = validSliderPosition.value,
                                onValueChange = { validSliderPosition.value = it },
                                valueRange = minMaxHp.minHp.toFloat()..minMaxHp.maxHp.toFloat(),
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )

                            Spacer(modifier = Modifier.height(16.dp))
                        }


                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            modifier = Modifier.padding(paddingValues),
                            contentPadding = PaddingValues(8.dp)
                        ) {
                            items(pockemonCards) { card ->
                                PockemonCardItem(card = card){
                                    println("Card clicked: ${card.name}")
                                    onCardClick(card)
                                }
                            }
                        }
                    }
                }



        }
    )
}


