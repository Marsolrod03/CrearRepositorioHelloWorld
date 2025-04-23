package com.example.crearrepositorio.features.films.data.network

import com.example.crearrepositorio.BuildConfig
import com.example.crearrepositorio.common_data.ApiKeyInterceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    val url= "https://api.themoviedb.org/3/"
    val apiKey = BuildConfig.TMDB_API_KEY

    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(apiKey))
        .addInterceptor(loggingInterceptor)
        .build()

    private val retrofit : Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val service : MoviesService = retrofit.create(MoviesService::class.java)

}
