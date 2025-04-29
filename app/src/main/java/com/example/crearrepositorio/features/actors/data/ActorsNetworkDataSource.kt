package com.example.crearrepositorio.features.actors.data

import javax.inject.Inject

class ActorsNetworkDataSource @Inject constructor(
    private val actorsService: ActorsService){

    suspend fun fetchActors(page: Int): PagedResultDTO? {
        val response = actorsService.listActors(page)
        if (!response.isSuccessful) {
            throw Exception("Error getting the actors")
        }else{
            return response.body()
        }
    }
}