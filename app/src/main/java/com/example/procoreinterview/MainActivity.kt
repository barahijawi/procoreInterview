package com.example.procoreinterview

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.ui.graphics.toArgb
import com.example.procoreinterview.presentation.screens.PockemonListScreen
import com.example.procoreinterview.ui.theme.ProcoreTheme
import com.example.procoreinterview.ui.theme.gray
import com.example.procoreinterview.util.Navigation
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        window.statusBarColor = gray.toArgb()
        window.navigationBarColor = gray.toArgb()
        window.setTitle("Pockemon App")
        setContent {
            ProcoreTheme {
//                PockemonListScreen()
                Navigation()
            }
        }
    }
}
