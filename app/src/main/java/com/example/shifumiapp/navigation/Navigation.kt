// Navigation.kt
package com.example.shifumiapp.navigation

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.NavigationBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.shifumiapp.GameViewModel
import com.example.shifumiapp.screens.*

@Composable
fun AppNavigation(modifier: Modifier = Modifier, gameViewModel: GameViewModel) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = "home",
        modifier = modifier
    ) {
        composable("home") { HomeScreen(navController) }
        composable("playSolo") { PlayScreen(navController , gameViewModel) }
        composable("gamemode") { GamemodeScreen(navController,gameViewModel) }
        composable("playBot") { PlayBotScreen(navController, gameViewModel) }
    }
}