package com.example.youtube.model.popularVideos

import androidx.annotation.Keep
import com.example.youtube.model.playlists.Snippet
import com.google.gson.annotations.SerializedName

@Keep
data class Items (
    @SerializedName("snippet")
    val snippet: Snippet,
    @SerializedName("contentDetails")
    val contentDetails: ContentDetails,
    @SerializedName("statistics")
    val statistics: Statistics,
)

