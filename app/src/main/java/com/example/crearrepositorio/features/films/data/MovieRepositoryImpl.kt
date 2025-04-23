package com.example.crearrepositorio.features.films.data

import android.util.Log
import com.example.crearrepositorio.features.films.data.network.MoviesService
import com.example.crearrepositorio.features.films.data.network.RetrofitClient
import com.example.crearrepositorio.features.films.domain.MovieModel
import com.example.crearrepositorio.features.films.domain.MovieRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl : MovieRepository {

    override suspend fun createMovies(): Flow<List<MovieModel>> = flow {
        val service = RetrofitClient().service
        val response = service.getPopularMovies()

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
