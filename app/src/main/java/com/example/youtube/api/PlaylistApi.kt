package com.example.youtube.api

import com.example.youtube.model.playlists.PlaylistItems
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface PlaylistApi {
    @GET("playlists")
    suspend fun getAllPlaylist(
        @Query("part") part: String,
        @Query("channelId") channelId: String,
        @Query("maxResults") maxResults: Int,
    ) : Response<PlaylistItems>
}