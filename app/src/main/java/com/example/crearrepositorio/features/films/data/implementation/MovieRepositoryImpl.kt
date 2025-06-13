package com.example.crearrepositorio.features.films.data.implementation

import android.util.Log
import com.example.crearrepositorio.features.films.data.MoviesNetworkDataSource
import com.example.crearrepositorio.features.films.domain.model.MovieModel
import com.example.crearrepositorio.features.films.domain.repository.MovieRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MovieRepositoryImpl @Inject constructor(
    private val networkDataSource: MoviesNetworkDataSource
): MovieRepository {

    override suspend fun createMovies(): Flow<List<MovieModel>> = flow {
//        val client = RetrofitClient().retrofit
//        val response = client.create(MoviesService::class.java).getPopularMovies()

        try {
            val movies = networkDataSource.fetchPopularMovies()
            emit(movies)
        } catch (e: Exception) {
            Log.e("MovieRepositoryImpl", "Error al obtener las películas", e)
        }
    }
}