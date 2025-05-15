package com.example.crearrepositorio.features.films.data.dataSource

import com.example.crearrepositorio.features.films.data.dto.MovieDTO
import com.example.crearrepositorio.features.films.data.dto.MoviePageDTO
import com.example.crearrepositorio.features.films.data.network.MoviesService
import com.example.crearrepositorio.features.films.domain.MoviesAppError
import jakarta.inject.Inject
import retrofit2.Response

class MoviesNetworkDataSource @Inject constructor(
    private val moviesService: MoviesService
) {

    suspend fun fetchPopularMovies(page: Int): MoviePageDTO? {
        val response = moviesService.getPopularMovies(page)
        if (!response.isSuccessful) {
            throw handleErrors(response)
        } else {
            return response.body()
        }
    }

    suspend fun fetchDetailMovies(movieId: String): MovieDTO? {
        val response = moviesService.getDetailMovies(movieId)
        if (!response.isSuccessful) {
            throw handleErrors(response)
        } else {
            return response.body()
        }
    }

    fun <T> handleErrors(response: Response<T>): Throwable = when (response.code()) {
        401 -> MoviesAppError.Unauthorized
        403 -> MoviesAppError.Forbidden
        400 -> MoviesAppError.BadRequest
        404 -> MoviesAppError.NotFound
        in 500..599 -> MoviesAppError.ServerError
        else -> MoviesAppError.UnknownError()
    }
}