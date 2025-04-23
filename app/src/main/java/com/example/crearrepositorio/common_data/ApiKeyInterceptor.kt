package com.example.crearrepositorio.common_data

import okhttp3.Interceptor
import okhttp3.Response


class ApiKeyInterceptor(private val apiKey: String) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()

        val interceptedUrl =
            request.newBuilder().addHeader("Authorization", "Bearer $apiKey")
                .addHeader("accept", "application/json").build()

        return chain.proceed(interceptedUrl)
    }
}