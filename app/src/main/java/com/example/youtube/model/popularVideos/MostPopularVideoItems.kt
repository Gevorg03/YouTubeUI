package com.example.youtube.model.popularVideos

import androidx.annotation.Keep
import com.google.gson.annotations.SerializedName

@Keep
data class MostPopularVideoItems(
    @SerializedName("items")
    val items: List<Items>
)