package com.example.crearrepositorio.features.actors.data

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ActorsService {
    @GET("person/popular")
    suspend fun listActors(
        @Query("page") page: Int
    ): Response<PagedResultDTO>
}