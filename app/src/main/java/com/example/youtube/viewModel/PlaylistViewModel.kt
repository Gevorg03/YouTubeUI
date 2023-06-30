package com.example.youtube.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.youtube.network.ApiState
import com.example.youtube.repository.PlaylistRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PlaylistViewModel
@Inject constructor(
    private val repository: PlaylistRepository
) : ViewModel() {

    private val _playlist: MutableStateFlow<ApiState> =
        MutableStateFlow(ApiState.Empty)

    val playlist: StateFlow<ApiState>
        get() = _playlist

    init {
        getPlaylists()
    }

    private fun getPlaylists() = viewModelScope.launch {
        repository.getPlaylist(
            "snippet",
            "UC_x5XG1OV2P6uZZ5FSM9Ttw",
            25
        ).catch { e ->
                _playlist.value = ApiState.Failure(e)
            }
            .collect { data ->
                _playlist.value = ApiState.Success(data)
            }
    }
}