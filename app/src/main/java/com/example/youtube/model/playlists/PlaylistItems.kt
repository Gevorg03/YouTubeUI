package com.example.youtube.model.playlists

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class PlaylistItems(
    @SerializedName("items")
    val items: List<Items>
)