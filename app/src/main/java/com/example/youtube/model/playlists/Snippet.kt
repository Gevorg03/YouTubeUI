package com.example.youtube.model.playlists

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Snippet(
    @SerializedName("publishedAt")
    val publishedAt: String,

    @SerializedName("channelId")
    val channelId: String,

    @SerializedName("title")
    val title: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("thumbnails")
    val thumbnails: Thumbnails,

    @SerializedName("channelTitle")
    val channelTitle: String

)