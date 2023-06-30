package com.example.youtube.repository

import com.example.youtube.api.PlaylistApi
import com.example.youtube.model.playlists.PlaylistItems
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class PlaylistRepository
@Inject constructor(
    private val api: PlaylistApi
) {
    suspend fun getPlaylist(
        part: String,
        channelId: String,
        maxResults: Int
    ): Flow<Response<PlaylistItems>> = flow {
        emit(api.getAllPlaylist(part, channelId, maxResults))
    }.flowOn(Dispatchers.IO)
}