package com.example.youtube.screens

import android.annotation.SuppressLint
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavDestination
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.youtube.R
import com.example.youtube.bottomBarNavigation.BottomBarScreen
import com.example.youtube.bottomBarNavigation.BottomNavGraph
import com.example.youtube.viewModel.PlaylistViewModel
import com.google.accompanist.systemuicontroller.rememberSystemUiController

@SuppressLint("UnusedMaterialScaffoldPaddingParameter")
@Composable
fun MainScreen(playlistViewModel: ViewModel) {
    val navController = rememberNavController()

    Scaffold(
        topBar = {
            TopAppBar()
        },
        bottomBar = {
            BottomBar(navController = navController)
        },
        modifier = Modifier.navigationBarsPadding()
    ) {
        SetStatusBarColor(statusBarColor = Color.Black)
        BottomNavGraph(
            navController = navController,
            playlistViewModel = playlistViewModel
        )
    }
}

@Composable
fun SetStatusBarColor(statusBarColor: Color) {
    val systemUiController = rememberSystemUiController()

    SideEffect {
        systemUiController.setStatusBarColor(
            color = statusBarColor,
        )
    }
}

@Composable
fun BottomBar(
    navController: NavHostController
) {
    val screens = listOf(
        BottomBarScreen.Home,
        BottomBarScreen.Shorts,
        BottomBarScreen.Add,
        BottomBarScreen.Subscriptions,
        BottomBarScreen.Library,
    )

    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination

    BottomAppBar(
        backgroundColor = Color.Black,
    ) {
        screens.forEach { screen ->
            AddItem(
                screen = screen,
                currentDestination = currentDestination,
                navController = navController
            )
        }
    }
}

@Composable
fun RowScope.AddItem(
    screen: BottomBarScreen,
    currentDestination: NavDestination?,
    navController: NavHostController
) {
    val selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true

    val contentColor = if (selected) Color.Red else Color.White

    BottomNavigationItem(
        label = {
            Text(
                text = screen.title,
                maxLines = 1,
                color = Color.White
            )
        },
        icon = {
            Icon(
                painter = painterResource(id = screen.icon),
                contentDescription = "Navigation Icon",
                tint = contentColor
            )

        },
        selected = selected,
        unselectedContentColor = LocalContentColor.current.copy(alpha = ContentAlpha.disabled),
        selectedContentColor = Color.White,
        onClick = {
            navController.navigate(screen.route) {
                popUpTo(navController.graph.findStartDestination().id)
                launchSingleTop = true
            }
        }
    )
}

@Composable
fun TopAppBar() {
    TopAppBar(
        title = {
            Row(
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.youtube_icon),
                    contentDescription = "youtubeIcon",
                    tint = Color.Unspecified,
                    modifier = Modifier.width(26.dp)
                )
                Text(
                    text = "YouTube",
                    color = Color.White,
                    modifier = Modifier
                        .padding(start = 2.dp)
                )
            }

        },
        actions = {
            TopAppBarIcons()
        },
        backgroundColor = Color.Black,
        elevation = 10.dp
    )
}

@Composable
fun TopAppBarIcons() {
    val icons = listOf(
        R.drawable.ic_connec_tv, R.drawable.ic_notification,
        R.drawable.ic_search, R.drawable.ic_person
    )
    val descriptions = listOf("tv", "notification", "search", "person")

    icons.forEachIndexed { index, element ->
        AppBarIcon(
            icon = element,
            description = descriptions[index]
        )
    }
}

@Composable
fun AppBarIcon(
    @DrawableRes icon: Int,
    description: String,
//    action: () -> Unit
) {
    IconButton(
        onClick = {}
    ) {
        if (description != "notification") {
            Icon(
                painter = painterResource(id = icon),
                contentDescription = description,
                tint = Color.White
            )
        }
        else {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                BadgedBox(
                    badge = {
                        Badge {
                            Text(
                                text = "9+",
                                color = Color.White,
                                modifier = Modifier
                                    .background(Color.Red)
                            )
                        }
                    }
                ) {
                    Icon(
                        painter = painterResource(id = icon),
                        contentDescription = description,
                        tint = Color.White
                    )
                }
            }
        }
    }
}

