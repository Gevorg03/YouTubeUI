package com.example.youtube.navigation

sealed class StartScreen(val route: String) {
    object Splash : StartScreen("splash_screen")
    object Home : StartScreen("home_screen")
}