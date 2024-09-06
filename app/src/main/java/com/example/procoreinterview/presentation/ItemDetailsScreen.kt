package com.example.procoreinterview.presentation

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.CircularProgressIndicator
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier

import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.procoreinterview.presentation.viewmodel.ItemViewModel

@Composable
fun ItemDetailsScreen(
    navController: NavController,
    itemId: String,
    viewModel: ItemViewModel = hiltViewModel()
)
{

    val itemDetails by viewModel.itemDetails.collectAsState()

    // Fetch the item details when the screen is loaded
    LaunchedEffect(itemId) {
        viewModel.fetchItemDetails(itemId)
    }

    itemDetails?.let {
        // Display item details here
        Box(modifier = Modifier.fillMaxSize())
        {
            Text(text = itemDetails?.moreDetails ?: "no Data")
            // Example: Back navigation
            Button(onClick = { navController.popBackStack() }) {
                Text("Go Back")
            }
        }
    } ?: run {
        // Show loading or error state if itemDetails is null
        CircularProgressIndicator(modifier = Modifier.fillMaxSize(.2f))
    }
}