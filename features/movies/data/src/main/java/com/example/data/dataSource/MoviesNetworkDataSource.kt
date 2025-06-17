package com.example.data.dataSource


import com.example.domain.MoviesAppError
import com.example.data.dto.MovieDTO
import com.example.data.dto.MoviePageDTO
import com.example.data.network.MoviesService
import jakarta.inject.Inject
import retrofit2.Response

class MoviesNetworkDataSource @Inject constructor(
    private val moviesService: MoviesService
) {

    suspend fun fetchPopularMovies(page: Int, region: String): MoviePageDTO? {
        val response : Response<MoviePageDTO> = moviesService.getPopularMovies(page, region)
        if (!response.isSuccessful) {
            throw handleErrors(response)
        } else {
            return response.body()
        }
    }

    suspend fun fetchDetailMovies(movieId: Int): MovieDTO? {
        val response : Response<MovieDTO> = moviesService.getDetailMovies(movieId)
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