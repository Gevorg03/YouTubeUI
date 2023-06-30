package com.example.youtube.hilt

import com.example.youtube.BuildConfig
import com.example.youtube.api.MostPopularVideosApi
import com.example.youtube.api.PlaylistApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import javax.inject.Singleton
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideBaseUrl(): String =
        "https://youtube.googleapis.com/youtube/v3/"

    @Singleton
    @Provides
    fun getPlaylistRetrofitServiceInstance(retrofit: Retrofit): PlaylistApi =
        retrofit.create(PlaylistApi::class.java)

    @Singleton
    @Provides
    fun getVideoRetrofitServiceInstance(retrofit: Retrofit): MostPopularVideosApi =
        retrofit.create(MostPopularVideosApi::class.java)


    @Singleton
    @Provides
    fun getRetrofitInstance(url: String, client: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .baseUrl(url)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()


    @Singleton
    @Provides
    fun loggingInterceptor(): HttpLoggingInterceptor =
        HttpLoggingInterceptor().setLevel(
            if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BODY
            else HttpLoggingInterceptor.Level.NONE
        )

    @Singleton
    @Provides
    fun client(loggingInterceptor: HttpLoggingInterceptor): OkHttpClient =
        OkHttpClient.Builder()
            .addInterceptor(loggingInterceptor)
            .addInterceptor { chain ->
                val url = chain
                    .request()
                    .url
                    .newBuilder()
                    .addQueryParameter("key", BuildConfig.YOUTUBE_API_KEY)
                    .build()
                chain.proceed(chain.request().newBuilder().url(url).build())
            }
            .build()
}