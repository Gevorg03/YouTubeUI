package com.example.youtube.model.playlists

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class Items (
    @SerializedName("snippet")
    val snippet: Snippet
)