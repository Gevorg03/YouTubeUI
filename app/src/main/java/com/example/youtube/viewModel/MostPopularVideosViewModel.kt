package com.example.youtube.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtube.network.ApiState
import com.example.youtube.repository.MostPopularVideosRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MostPopularVideosViewModel
@Inject constructor(
    private val repository: MostPopularVideosRepository
) : ViewModel() {

    private val _video: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)

    val video: StateFlow<ApiState>
        get() = _video

    init {
        getMostPopularVideos()
    }

    fun getMostPopularVideos() = viewModelScope.launch {
        _video.value = ApiState.Loading

        repository.getPopularVideos(
            "snippet,contentDetails,statistics",
            "mostPopular",
            25
        ).catch { e ->
            _video.value = ApiState.Failure(e)
        }
            .collect { data ->
                _video.value = ApiState.Success(data)
            }
    }
}