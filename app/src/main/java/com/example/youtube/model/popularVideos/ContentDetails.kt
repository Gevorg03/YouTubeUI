package com.example.youtube.model.popularVideos

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class ContentDetails (
    @SerializedName("duration")
    val duration: String,
    @SerializedName("dimension")
    val dimension: String,
)