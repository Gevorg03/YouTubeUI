package com.example.youtube.network

import retrofit2.Response

sealed class ApiState {
    object Loading : ApiState()
    class Failure(val msg: Throwable) : ApiState()
    class Success<T>(val data: Response<T>) : ApiState()
    object Empty : ApiState()
}