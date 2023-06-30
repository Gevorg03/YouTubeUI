package com.example.youtube.bottomBarNavigation

import androidx.compose.runtime.Composable
import androidx.lifecycle.ViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.youtube.screens.HomeScreen
import com.example.youtube.screens.OtherScreen
import com.example.youtube.viewModel.PlaylistViewModel

@Composable
fun BottomNavGraph(
    navController: NavHostController,
    playlistViewModel: ViewModel
) {
    NavHost(
        navController = navController,
        startDestination = BottomBarScreen.Home.route
    ) {

        composable(BottomBarScreen.Home.route) {
            HomeScreen(playlistViewModel)
        }

        composable(BottomBarScreen.Shorts.route) {
            OtherScreen("Shorts")
        }

        composable(BottomBarScreen.Add.route) {
            OtherScreen("Add")
        }

        composable(BottomBarScreen.Subscriptions.route) {
            OtherScreen("Subscriptions")
        }

        composable(BottomBarScreen.Library.route) {
            OtherScreen("Library")
        }
    }
}