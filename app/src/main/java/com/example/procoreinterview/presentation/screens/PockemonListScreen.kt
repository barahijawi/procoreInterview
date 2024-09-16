package com.example.procoreinterview.presentation.screens

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.grid.rememberLazyGridState
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.DropdownMenu
import androidx.compose.material.DropdownMenuItem
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TextButton
import androidx.compose.material.TopAppBar
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
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.example.procoreinterview.domain.PockemonCard
import com.example.procoreinterview.presentation.viewmodel.PockemonUiState
import com.example.procoreinterview.presentation.viewmodel.PockemonViewModel

@Composable
fun PockemonListScreen(onCardClick: (PockemonCard) -> Unit, viewModel: PockemonViewModel = hiltViewModel()) {
    // Collect the UI state from the ViewModel
    val pockemonUiState by viewModel.pockemonUiState.collectAsState()
    var expanded by remember { mutableStateOf(false) } // For dropdown menu
    var selectedSort by remember { mutableStateOf("Sort By") } // To track selected option


    Scaffold(
        topBar = {
            TopAppBar(title = { Text(text = "Pokemon List") },
                      actions = {
                          // Dropdown button in the top bar
                          Box {
                              TextButton(onClick = { expanded = true }) {
                                  Text(text = selectedSort, color = Color.White)
                              }
                              DropdownMenu(
                                  expanded = expanded,
                                  onDismissRequest = { expanded = false }
                              ) {
                                  DropdownMenuItem(onClick = {
                                      selectedSort = "Sort by HP"
                                      viewModel.sortBy("hp") // Call ViewModel to sort by HP
                                      expanded = false
                                  }) {
                                      Text("Sort by HP")
                                  }
                                  DropdownMenuItem(onClick = {
                                      selectedSort = "Sort by Type"
                                      viewModel.sortBy("type") // Call ViewModel to sort by type
                                      expanded = false
                                  }) {
                                      Text("Sort by Type")
                                  }
                              }
                          }
                      }
                      )
        },
        content = { paddingValues ->
            Box(modifier = Modifier.padding(paddingValues)) {
                when (pockemonUiState) {
                    is PockemonUiState.Loading -> {
                        // Show a loading indicator while data is being fetched
                        CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                    }
                    is PockemonUiState.Success -> {
                        // Show the Pokemon list when data is successfully loaded
                        val cards = (pockemonUiState as PockemonUiState.Success).data
                        LazyVerticalGrid(
                            columns = GridCells.Fixed(4),
                            contentPadding = PaddingValues(8.dp),
//                            state = gridState

                        ) {
                            items(cards) { card ->
                                PockemonCardItem(card = card){
                                    onCardClick(card)
                                }
                            }
                            if (cards.isNotEmpty() && cards.size < viewModel.totalCount.value)
                            {
                                item {
                                    LaunchedEffect(Unit) {
                                        viewModel.fetchPockemonCards() // Load more data when nearing the end
                                    }
                                    CircularProgressIndicator()
                                }
                            }
                        }
                    }
                    is PockemonUiState.Error -> {
                        // Show an error message when something goes wrong
                        val errorMessage = (pockemonUiState as PockemonUiState.Error).message
                        Text(
                            text = errorMessage,
                            color = Color.Red,
                            modifier = Modifier.align(Alignment.Center)
                        )
                    }
                }
            }
        }
    )
}
