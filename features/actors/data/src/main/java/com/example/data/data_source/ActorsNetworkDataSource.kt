package com.example.data.data_source

import com.example.data.dto.PagedResultDTO
import com.example.data.services.ActorsService
import com.example.domain.models.ActorModel
import javax.inject.Inject

class ActorsNetworkDataSource @Inject constructor(
    private val actorsService: ActorsService
) {

    suspend fun fetchActors(page: Int): PagedResultDTO? {
        val response = actorsService.listActors(page)
        if (!response.isSuccessful) {
            throw Exception("Error getting the actors")
        } else {
            return response.body()
        }
    }

    suspend fun fetchDetails(actorId: String): ActorModel? {
        val response = actorsService.actorDetails(actorId)
        if (response.isSuccessful) {
            return response.body()
        } else {
            throw Exception("Error getting the details")
        }
    }
}