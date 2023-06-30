package com.example.youtube.repository

import com.example.youtube.api.MostPopularVideosApi
import com.example.youtube.model.playlists.PlaylistItems
import com.example.youtube.model.popularVideos.MostPopularVideoItems
import com.example.youtube.viewModel.MostPopularVideosViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import retrofit2.Response
import javax.inject.Inject

class MostPopularVideosRepository
@Inject constructor(
    private val api: MostPopularVideosApi
) {
    suspend fun getPopularVideos(
        part: String,
        chart: String,
        maxResults: Int
    ): Flow<Response<MostPopularVideoItems>> = flow {
        emit(api.getPopularVideos(part, chart, maxResults))
    }.flowOn(Dispatchers.IO)
}