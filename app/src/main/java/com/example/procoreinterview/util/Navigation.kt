package com.example.procoreinterview.util

import android.net.Uri
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.procoreinterview.presentation.screens.PockemonDetailsScreen
import com.example.procoreinterview.presentation.screens.PockemonListScreen

@OptIn(ExperimentalAnimationApi::class)
@Composable
fun Navigation() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.ListScreen.route
    ) {
        // List Screen
        composable(ScreenRoutes.ListScreen.route) {
            PockemonListScreen(

                onCardClick = { pockemon ->
                    navController.navigate(
                        ScreenRoutes.DetailsScreen.createRoute(
                            pockemon.id,
                            pockemon.name,
                            pockemon.hp,
                            Uri.encode(pockemon.imageUrlLarge),

                            pockemon.superType,
                            pockemon.types.joinToString(",")
                        )
                    )
                }
            )
        }

        // Details Screen
        composable(ScreenRoutes.DetailsScreen.route,

        ) { backStackEntry ->
            val pokemonId = backStackEntry.arguments?.getString("pokemonId") ?: ""
            val pokemonName = backStackEntry.arguments?.getString("pokemonName") ?: ""
            val pokemonHp = backStackEntry.arguments?.getString("pokemonHp") ?: ""
            val pokemonImageUrl = backStackEntry.arguments?.getString("pokemonImageUrl") ?: ""
            val pokemonSuperType = backStackEntry.arguments?.getString("pokemonSuperType") ?: ""
            val pokemonTypes = backStackEntry.arguments?.getString("pokemonTypes") ?: ""

            PockemonDetailsScreen(
                pokemonId = pokemonId,
                pokemonName = pokemonName,
                pokemonHp = pokemonHp,
                pokemonImageUrl = pokemonImageUrl,
                pokemonSuperType = pokemonSuperType,
                pokemonTypes = pokemonTypes,
                onBackPressed = { navController.popBackStack() }
            )
        }
    }
}

sealed class ScreenRoutes(val route: String) {
    object ListScreen : ScreenRoutes("list_screen")
    object DetailsScreen : ScreenRoutes("details_screen/{pokemonId}/{pokemonName}/{pokemonHp}/{pokemonImageUrl}/{pokemonSuperType}/{pokemonTypes}") {
        fun createRoute(
            pokemonId: String,
            pokemonName: String,
            pokemonHp: String,
            pokemonImageUrl: String,
            pokemonSuperType: String?,
            pokemonTypes: String
        ) = "details_screen/$pokemonId/$pokemonName/$pokemonHp/$pokemonImageUrl/$pokemonSuperType/$pokemonTypes"
    }
}
