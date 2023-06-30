package com.example.youtube.api

import com.example.youtube.model.playlists.PlaylistItems
import com.example.youtube.model.popularVideos.MostPopularVideoItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface MostPopularVideosApi {
    @GET("videos")
    suspend fun getPopularVideos(
        @Query("part") part: String,
        @Query("chart") chart: String,
        @Query("maxResults") maxResults: Int,
    ) : Response<MostPopularVideoItems>
}