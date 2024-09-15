package com.example.procoreinterview.util

import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun PockemonSortingOptions(
    onSortChange: (SortOption) -> Unit // Callback to notify sorting change
) {
    var expanded by remember { mutableStateOf(false) }
    var selectedOption by remember { mutableStateOf(SortOption.NONE) }

    Box(modifier = Modifier.fillMaxWidth().padding(16.dp)) {
        Button(onClick = { expanded = !expanded }) {
            Text(text = "Sort by: ${selectedOption.displayName}")
        }

        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            DropdownMenuItem(onClick = {
                selectedOption = SortOption.TYPE
                expanded = false
                onSortChange(SortOption.TYPE) // Notify parent of sorting change
            }) {
                Text("Sort by Type")
            }
            DropdownMenuItem(onClick = {
                selectedOption = SortOption.HP
                expanded = false
                onSortChange(SortOption.HP) // Notify parent of sorting change
            }) {
                Text("Sort by HP")
            }
        }
    }
}

enum class SortOption(val displayName: String) {
    NONE("None"),
    TYPE("Type"),
    HP("HP")
}
