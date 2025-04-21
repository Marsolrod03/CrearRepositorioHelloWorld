package com.example.crearrepositorio.features.series.data.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitClient {
    private val url = "https://api.themoviedb.org/3/"
    private val key = "eyJhbGciOiJIUzI1NiJ9.eyJhdWQiOiIyOGRiZmNkNjdkZWVlMTA1MDFlMGJiYTJjNjc3N2NiYiIsIm5iZiI6MTc0NDAyNTMzOC4xOTcsInN1YiI6IjY3ZjNiNmZhZTFkNWMyM2M2ZWQ5N2Y4YyIsInNjb3BlcyI6WyJhcGlfcmVhZCJdLCJ2ZXJzaW9uIjoxfQ.QWISux4YTmizIldH5BIdkVP6W7SbEzzTOAF6nmiVA4M"


    private val loggingInterceptor = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    private val client = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor(key))
        .addInterceptor(loggingInterceptor)
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(url)
        .client(client)

        .addConverterFactory(GsonConverterFactory.create())
        .build()

}