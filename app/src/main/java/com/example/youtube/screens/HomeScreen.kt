package com.example.youtube.screens

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import coil.compose.rememberAsyncImagePainter
import com.example.youtube.R
import com.example.youtube.model.playlists.Snippet
import com.example.youtube.model.popularVideos.ContentDetails
import com.example.youtube.model.popularVideos.MostPopularVideoItems
import com.example.youtube.model.popularVideos.Statistics
import com.example.youtube.network.ApiState
import com.example.youtube.ui.theme.MainBackgroundColor
import com.example.youtube.viewModel.MostPopularVideosViewModel
import com.google.accompanist.swiperefresh.SwipeRefresh
import com.google.accompanist.swiperefresh.rememberSwipeRefreshState

@Composable
fun HomeScreen(
    mostPopularViewModel: ViewModel
) {

    val feedbacks = listOf(
        "All", "Sketch Comedy", "Mixes", "Music", "Live", "Gaming",
        "Television series", "Computer programming", "Animated films"
    )
    val videoItems = getData(
        mostPopularVideosViewModel = mostPopularViewModel
                as MostPopularVideosViewModel
    )

    val apiState = mostPopularViewModel.video.collectAsState().value
    val swipeRefreshState = rememberSwipeRefreshState(
        isRefreshing = apiState is ApiState.Loading
    )
    
    SwipeRefresh(
        state = swipeRefreshState,
        onRefresh = mostPopularViewModel::getMostPopularVideos
    ) {
        Column(
            modifier = Modifier
                .background(Color.Black)
        ) {
            VideoSection(
                videoItems = videoItems,
                feedbacks = feedbacks
            )
        }
    }


}

@Composable
fun getData(
    mostPopularVideosViewModel: MostPopularVideosViewModel
): MostPopularVideoItems {
    val apiState = mostPopularVideosViewModel.video.collectAsState().value
    var videos = MostPopularVideoItems(emptyList())

    when (apiState) {
        is ApiState.Failure -> {
            Log.d("main", "onCreate: ${apiState.msg}")
        }

        is ApiState.Success<*> -> {
            val data = apiState.data.body()
            if (data != null)
                videos = data as MostPopularVideoItems
        }

        else -> {}
    }

    return videos
}

@Composable
fun Feedbacks(
    feedbacks: List<String>
) {
    var selectedFeedbackIndex by remember {
        mutableIntStateOf(0)
    }

    LazyRow {
        items(feedbacks.size) { item ->
            Box(
                modifier = Modifier
                    .padding(start = 5.dp, top = 5.dp, end = 5.dp)
                    .clickable {
                        selectedFeedbackIndex = item
                    }
                    .clip(RoundedCornerShape(10.dp))
                    .background(
                        if (selectedFeedbackIndex == item) Color.White
                        else MainBackgroundColor
                    )
                    .padding(7.dp)
            ) {
                Text(
                    text = feedbacks[item],
                    color = if (selectedFeedbackIndex == item) Color.Black
                    else Color.White
                )
            }
        }
    }
}

@Composable
fun VideoSection(
    videoItems: MostPopularVideoItems,
    feedbacks: List<String>,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
    ) {
        LazyColumn(
            contentPadding = PaddingValues(bottom = 56.dp),
            modifier = Modifier
                .fillMaxHeight()
        ) {
            item {
                Feedbacks(feedbacks = feedbacks)
            }
            items(videoItems.items.size) {
                VideoItem(
                    videoItems.items[it].snippet,
                    videoItems.items[it].contentDetails,
                    videoItems.items[it].statistics,
                )
            }
        }
    }
}

@Composable
fun VideoItem(
    snippet: Snippet,
    contentDetails: ContentDetails,
    statistics: Statistics,
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(bottom = 10.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
        ) {
            VideoImage(
                snippet = snippet,
                contentDetails = contentDetails
            )
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 10.dp)
            ) {
                VideoIcon()
                VideoNameAndDescription(
                    snippet = snippet,
                    contentDetails = contentDetails
                )
            }
        }
    }
}

@Composable
fun VideoImage(
    snippet: Snippet,
    contentDetails: ContentDetails,
) {
    Box {
        Image(
            painter = rememberAsyncImagePainter(snippet.thumbnails.high.url),
            contentDescription = snippet.description,
            modifier = Modifier
                .height(250.dp)
                .fillMaxWidth()
        )
        Text(
            text = duration(contentDetails.duration),
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(end = 10.dp, bottom = 15.dp)
                .background(Color.Black)
                .padding(start = 4.dp, end = 4.dp),
            fontSize = 15.sp,
            color = Color.White
        )
    }
}

@Composable
fun VideoIcon() {
    Box(
        modifier = Modifier
            .padding(top = 5.dp)
    ) {
        Image(
            painter = painterResource(R.drawable.channel_image),
            contentDescription = "channelImage",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(35.dp)
                .clip(CircleShape)
        )

    }
}

@Composable
fun VideoNameAndDescription(
    snippet: Snippet,
    contentDetails: ContentDetails
) {
    Column {
        Text(
            text = snippet.title,
            style = MaterialTheme.typography.headlineMedium,
            lineHeight = 26.sp,
            color = Color.White,
            fontSize = 20.sp,
            modifier = Modifier
                .padding(start = 10.dp)
        )
        Description(
            snippet = snippet,
            contentDetails = contentDetails
        )
    }
}

@Composable
fun Description(
    snippet: Snippet,
    contentDetails: ContentDetails
) {
    Row {
        Text(
            text = snippet.channelTitle,
            style = MaterialTheme.typography.headlineMedium,
            lineHeight = 26.sp,
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 10.dp, top = 5.dp)
        )

        Image(
            painterResource(id = R.drawable.ic_point),
            contentDescription = null,
            modifier = Modifier
                .padding(start = 5.dp, top = 13.dp)
                .size(3.dp)
        )

        Text(
            text = "${contentDetails.dimension} ago",
            style = MaterialTheme.typography.headlineMedium,
            lineHeight = 26.sp,
            color = Color.LightGray,
            fontSize = 12.sp,
            modifier = Modifier
                .padding(start = 5.dp, top = 5.dp)
        )
    }
}

fun duration(duration: String): String {
    val time = duration.substring(2)
    val splitedTime: MutableList<String> =
        time.split("H", "M", "S") as MutableList<String>
    splitedTime.removeAt(splitedTime.lastIndex)

    val second =
        if (splitedTime.last().length == 1) "0${splitedTime.last()}"
        else splitedTime.last()

    var dur = ""
    for (i in 0 until splitedTime.size - 1) {
        dur += splitedTime[i] + ":"
    }
    dur += second
    return dur
}