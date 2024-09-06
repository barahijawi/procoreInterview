package com.example.procoreinterview.util

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.example.procoreinterview.presentation.FinalDestination
import com.example.procoreinterview.presentation.ItemDetailsScreen
import com.example.procoreinterview.presentation.ItemListScreen


@Composable
fun Navigation() {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = ScreenRoutes.ItemListScreen.route
    ) {
        composable(ScreenRoutes.ItemListScreen.route){
            ItemListScreen(navController)
        }

        composable(ScreenRoutes.FinalDestination.route){
            FinalDestination()
        }

//         Define the route for ItemDetailsScreen with the itemId argument
        composable(
            route = ScreenRoutes.ItemDetailsScreen.route,
            arguments = listOf(navArgument("itemId") { type = NavType.StringType })
        ) { backStackEntry ->
            val itemId = backStackEntry.arguments?.getString("itemId") ?: ""
            ItemDetailsScreen(navController, itemId)
        }
    }
}

sealed class ScreenRoutes(val route: String) {
    object ItemListScreen : ScreenRoutes("item_list_screen")
    object FinalDestination : ScreenRoutes("final_destination")
    object ItemDetailsScreen: ScreenRoutes("item_details_screen/{itemId}"){
        fun createRoute(itemId:String) = "item_details_screen/$itemId"
    }
}