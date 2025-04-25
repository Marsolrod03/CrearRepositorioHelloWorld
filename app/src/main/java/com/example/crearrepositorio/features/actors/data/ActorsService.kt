package com.example.crearrepositorio.features.actors.data

import retrofit2.Response
import retrofit2.http.GET

interface ActorsService {
    @GET("person/popular")
    suspend fun listActors(): Response<PagedResultDTO>
}