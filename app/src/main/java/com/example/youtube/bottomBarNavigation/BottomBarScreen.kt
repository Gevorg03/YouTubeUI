package com.example.youtube.bottomBarNavigation

import com.example.youtube.R

sealed class BottomBarScreen(
    val route: String,
    val title: String,
    val icon: Int,
) {
    object Home : BottomBarScreen(
        route = "home_screen",
        title = "Home",
        icon = R.drawable.ic_home
    )

    object Shorts : BottomBarScreen(
        route = "shorts_screen",
        title = "Shorts",
        icon = R.drawable.ic_short
    )

    object Add : BottomBarScreen(
        route = "add_screen",
        title = "Add",
        icon = R.drawable.ic_circle_add
    )

    object Subscriptions : BottomBarScreen(
        route = "subscriptions_screen",
        title = "Subscriptions",
        icon = R.drawable.ic_subscription
    )

    object Library : BottomBarScreen(
        route = "library_screen",
        title = "Library",
        icon = R.drawable.ic_library
    )
}