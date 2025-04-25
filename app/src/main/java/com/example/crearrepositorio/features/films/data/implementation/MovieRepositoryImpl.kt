package com.example.crearrepositorio.features.films.data.implementation

import android.util.Log
import com.example.crearrepositorio.common_data.RetrofitClient
import com.example.crearrepositorio.features.films.data.mapper.toMovieModel
import com.example.crearrepositorio.features.films.data.network.MoviesService
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl @Inject constructor(): MovieRepository {

    override suspend fun createMovies(): Flow<List<MovieModel>> = flow {
        val client = RetrofitClient().retrofit
        val response = client.create(MoviesService::class.java).getPopularMovies()

        try {
            if (response.isSuccessful) {
                response.body()?.results?.let { movies ->
                    emit(movies.map { movie ->
                        movie.toMovieModel()
                    })
                }
            }
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "Error al obtener las películas", e)
        }
    }
}