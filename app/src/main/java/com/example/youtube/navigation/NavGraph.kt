package com.example.youtube.navigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.youtube.screens.MainScreen
import com.example.youtube.screens.SplashScreen
import com.example.youtube.viewModel.PlaylistViewModel

@Composable
fun SetupNavGraph(playlistItems: ViewModel) {
    val navController = rememberNavController()
    NavHost(
        navController = navController,
        startDestination = StartScreen.Splash.route
    ) {
        composable(StartScreen.Splash.route) {
            SplashScreen(navController = navController)
        }

        composable(StartScreen.Home.route) {
            MainScreen(playlistItems)
        }
    }
}