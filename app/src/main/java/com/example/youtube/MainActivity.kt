package com.example.youtube

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Surface
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import com.example.youtube.navigation.SetupNavGraph
import com.example.youtube.network.ApiState
import com.example.youtube.ui.theme.MainBackgroundColor
import com.example.youtube.viewModel.MostPopularVideosViewModel
import com.example.youtube.viewModel.PlaylistViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private val playlistViewModel: MostPopularVideosViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Surface(
                color = MainBackgroundColor,
                modifier = Modifier.fillMaxSize()
            ) {
                SetupNavGraph(playlistViewModel)
            }
        }
    }
}


