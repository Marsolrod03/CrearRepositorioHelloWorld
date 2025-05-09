package com.example.crearrepositorio.features.actors.data

import com.example.crearrepositorio.features.actors.domain.ActorModel
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface ActorsService {
    @GET("person/popular")
    suspend fun listActors(
        @Query("page") page: Int
    ): Response<PagedResultDTO>

    @GET("person/{person_id}")
    suspend fun actorDetails(
        @Path("person_id") actorId: String
    ): Response<ActorModel>
}